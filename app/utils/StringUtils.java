package utils;

public class StringUtils {

	public static String[] splitStringToTwo(String text, int maxWords) {
		text = text.trim();
		StringBuilder firstLine = new StringBuilder();
		StringBuilder secondLine = new StringBuilder();
		String[] words = text.split(" ");
				
		for(String word: words){
			if(firstLine.length() + word.length() < maxWords){
				firstLine.append(word + " ");
				firstLine.append(" ");
			}else{
				secondLine.append(word);
				secondLine.append(" ");
			}
		}
		return new String[]{firstLine.toString(), secondLine.toString()};
	}
}
