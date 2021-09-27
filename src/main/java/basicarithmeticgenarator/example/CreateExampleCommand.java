package basicarithmeticgenarator.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateExampleCommand {

    private int examplePcs;

    private int operandPcs;

    private int operandRangeMin;

    private int operandRangeMax;

    
    private Operator operator;

    
    private UnknownField unknownField;
}
