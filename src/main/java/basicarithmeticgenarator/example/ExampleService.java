package basicarithmeticgenarator.example;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class ExampleService {

    private Random rnd = new Random();

    private List<String[]> examples;

    private List<ExampleDTO> examplesWithHtml = new ArrayList<>();

    private String htmlExamples = "";

    private static final String HEADER = """
            <div class="col-sm-6 col-md-4 example" style="padding: 20px">
                <div class="input-group mb-3 flex-nowrap">""";

    private static final String FOOTER1 = """
            \t</div>""";

    private static final String FOOTER2 = """
            </div>""";



    public String createExample(CreateExampleCommand command) {
        examples = new ArrayList<>();
        return buildExampleWithHtml(command, createRawExample(command));
    }


    private List<String[]> createRawExample(CreateExampleCommand command) {

        ExpressionParser parser = new SpelExpressionParser();
        StringBuilder sb;

        for (int i = 0; i < command.getExamplePcs(); i++) {
            String[] example = new String[command.getOperandPcs() * 2 + 1];
            sb = new StringBuilder();

            for (int j = 0; j < command.getOperandPcs(); j+=2) {
                example[j] = String.valueOf(rnd.nextInt(command.getOperandRangeMax() - command.getOperandRangeMin() + 1) + command.getOperandRangeMin());
                sb.append(example[j]);
                example[j + 1] = command.getOperator().sign;
                sb.append(example[j + 1]);
            }

            example[example.length - 3] = String.valueOf(rnd.nextInt(command.getOperandRangeMax() - command.getOperandRangeMin() + 1) + command.getOperandRangeMin());
            sb.append(example[example.length - 3]);
            int result = parser.parseExpression(sb.toString()).getValue(Integer.class);
            example[example.length - 2] = "=";
            example[example.length - 1] = String.valueOf(result);
            examples.add(example);
        }

        return examples;
    }


    private String buildExampleWithHtml(CreateExampleCommand command, List<String[]> examples) {
        StringBuilder sb = new StringBuilder();
        String solution = "";

        for (int i = 0; i < examples.size(); i++) {
            int unknownIndex = indexOfUnknownField(command.getUnknownField(), command.getOperandPcs());
            sb.append(HEADER)
                    .append("\n\t\t");

            for (int j = 0; j < examples.get(i).length; j++) {

                if (unknownIndex == j) {
                    int inputFieldWidth = widthOfInputField(examples.get(i)[unknownIndex]);
                    sb.append("""
                            \t\t<input type="text" class="form-control" style="min-width: """)
                            .append(inputFieldWidth)
                            .append("px; max-width: ")
                            .append(inputFieldWidth)
                            .append("px\">\n");
                } else {
                    sb.append("""
                            \t\t<span class="input-group-text">""")
                            .append(examples.get(i)[j])
                            .append("</span>\n");
                }
            }
            sb.append(FOOTER1)
                .append("\n")
                .append("""
                        \t<span class="resultSpan" style="color: limegreen; font-weight: bold;" hidden>Megoldás:\t""")
                .append(examples.get(i)[unknownIndex])
                .append("</span>")
                .append("\n")
                .append(FOOTER2)
                .append("\n");

        }
        htmlExamples += sb.toString();

        return sb.toString();
    }


    private int widthOfInputField(String solution) {

        return 41 + (solution.length() - 1) * 10;
    }


    private int indexOfUnknownField(UnknownField field, int numberOfOperands) {
        return switch (field) {
            case OPERAND -> rnd.nextInt(numberOfOperands + 1) * 2;
            case OPERATOR -> rnd.nextInt(numberOfOperands - 1) * 2 + 1;
            case RESULT -> numberOfOperands * 2;
            default -> throw new IllegalArgumentException("Wrong field name: " + field);
        };
    }

    public ExampleDTO getExamples() {

        return new ExampleDTO(htmlExamples);
    }


    public void deleteExamples() {
        htmlExamples = "";
    }
}
