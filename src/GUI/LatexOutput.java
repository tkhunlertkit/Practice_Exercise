package GUI;

import Algorithm.MyAlgorithm;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MSDK on 7/8/16.
 */
public class LatexOutput {


    private static final int NUM_PROBLEMS = 60;
    private final int QUESTION_PER_PAGE = 10;
    private final int COLUMN_PER_PAGE   = 2;
    private final int QUESTION_PER_COL  = QUESTION_PER_PAGE / COLUMN_PER_PAGE;

    private List<MyAlgorithm> problems = new ArrayList<>();

    public LatexOutput(int numProblems) {

        for (int i=0; i<numProblems; i++) {
            MyAlgorithm p = MyAlgorithm.instantiateQuestion();
            p.generateQuestion(10);
            problems.add(p);
        }

        try {

            PrintWriter pw = new PrintWriter("tex/output.tex");
            PrintWriter solution = new PrintWriter("tex/solution.tex");

            generateHeader(pw, false);
            generateQuestions(pw, numProblems, false);
            generateFooter(pw);
            pw.close();

            generateHeader(solution, true);
            generateQuestions(solution, numProblems, true);
            generateFooter(solution);
            solution.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    private void generateQuestions(PrintWriter pw, int numProblems, boolean solution) {
        for (int i=0; i<numProblems/QUESTION_PER_PAGE; i++) {
            generatePage(pw, i, solution);
        }
    }

    private void generatePage(PrintWriter pw, int page, boolean solution) {
        pw.write("\t\\begin{multicols}{2}\n");
        generateMiniPage(pw, page * QUESTION_PER_PAGE, solution);
        pw.write("\t\t\\vfill\n");
        pw.write("\t\t\\columnbreak\n");
        generateMiniPage(pw, page * QUESTION_PER_PAGE + QUESTION_PER_COL, solution);
        pw.write("\t\\end{multicols}\n");
    }

    private void generateMiniPage(PrintWriter pw, int startQuestion, boolean solution) {
        pw.write("\t\t\\begin{minipage}[t][\\textheight]{\\linewidth}\n");
        for (int i=1; i<=QUESTION_PER_COL; i++) {
            int questionNum = startQuestion + i;
            pw.write("\t\t\t" + questionNum + ". $" + problems.get(questionNum-1).toLatexFormat() + "$\\\\\n");
            if (solution) {
                pw.write("\t\t\t$" + problems.get(questionNum-1).getAnswerLatex() + "$\\\\\n");
            }
            pw.write("\t\t\t\\vfill\n");
        }
        pw.write("\t\t\t\\vfill\n");
        pw.write("\t\t\\end{minipage}\n");
    }

    private void generateHeader(PrintWriter pw, boolean solution) {
        pw.write("\\documentclass{article}\n");
        pw.write("\\usepackage[margin=0.8in]{geometry}\n");
        pw.write("\\usepackage{multicol}\n");
        pw.write("\\usepackage{fancyhdr}\n");
        pw.write(" \n");
        pw.write("\\pagestyle{fancy}\n");
        pw.write("\\fancyhf{}\n");
        if (solution) {
            pw.write("\\rhead{solution}\n");
        }
        pw.write("\\lhead{\\today}\n");
        pw.write("\\cfoot{\\thepage}");
        pw.write("\n");
        pw.write("\\begin{document}\n");
    }

    private void generateFooter(PrintWriter pw) {
        pw.write("\\end{document}");
    }

    public static void main(String[] args) {
        new LatexOutput(NUM_PROBLEMS);
    }
}
