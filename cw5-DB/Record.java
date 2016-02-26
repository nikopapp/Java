import java.util.*;

class Record extends Token{
  int numOfFields;
  String valuesIn;
  List<String> values = new ArrayList<String>();
  Record(int numOfFields, String valuesIn){
    super(numOfFields,valuesIn);
    this.numOfFields=numOfFields;
    this.valuesIn=valuesIn;
    this.values=tokens;
  }
}
