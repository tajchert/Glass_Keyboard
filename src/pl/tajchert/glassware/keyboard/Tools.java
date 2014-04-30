package pl.tajchert.glassware.keyboard;


public class Tools {
	
	public static final String AWESOME_TAG = "NumPad";
	public static final int sliceSizeH = 5;//Horizontal
	public static final int sliceSizeV = 5;//Vertical
	public static String saved = "";//inputed so far text 
	public static final int inputLength = 3;//whole length of input
	
	public static final int maxHorizontalAngle = 50;//should be  = sliceSizeH *(1/2*longest row)
	public static final int maxVerticalAngle = 20;
	
	//QWERTY rows
	public static final String [] rows = {"0123456789", "qwertyuiop" , "asdfghjkl", "zxcvbnm"};
	
	public static int getRow(int degrees){
		int row = degrees/sliceSizeV;
		if(row<0){
			row = 0;
		}else if(row >= rows.length){
			row = (rows.length-1);
		}
		return row;
	}
	
	public static String[] getRowContent(int degrees, int rowNumber){
		String [] result = {"", "", ""};
		int position = degrees/sliceSizeH;
		if(position<0){
			position = 0;
		}else if(position >= rows[rowNumber].length()){
			position = (rows[rowNumber].length()-1);
		}
		//TODO
		result[0] = rows[rowNumber].substring(0, position);
		result[1] = rows[rowNumber].substring(position, position + 1);
		result[2] = rows[rowNumber].substring(position + 1, rows[rowNumber].length());
		return result;
	}

}
