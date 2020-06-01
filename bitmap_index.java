import java.util.Scanner;
class print{
	public void print(String s){
		System.out.print(s);
	}
	public void print(int s){
		System.out.print(s);
	}
	public void new_line(){
		System.out.println("");
	}

	public String and(String s1,String s2, String padding){

		int one = Integer.parseInt(s1,2);
		int two = Integer.parseInt(s2,2);
		int and = one & two;

		String temp_ans = Integer.toBinaryString(and);

		String ans = (padding + temp_ans).substring(temp_ans.length());
		return ans;
	}

	public String or(String s1, String s2, String padding){
		int one = Integer.parseInt(s1,2);
		int two = Integer.parseInt(s2,2);
		int and = one | two;
		String temp_ans = Integer.toBinaryString(and);
		String ans = (padding + temp_ans).substring(temp_ans.length());
		return ans;
	}
	
}
class attribute{
	String attribute_name;
	int record[];
	String bitmap[];
	int number_of_records;
	String padding="";
	attribute(int size, String name){
		record=new int[size];
		bitmap = new String[size];
		attribute_name=name;
		this.number_of_records = size;
		for (int i = 0;i < size ;i++ ) {
			padding +="0";
		}

	}
	
	public void scan_record_values(Scanner in){
			print obj=new print();
			obj.print("Enter values for ");
			obj.print(this.attribute_name);
			obj.print(" attribute :");
			for (int i=0;i<this.number_of_records ;i++ ) {
					int x=in.nextInt();
					this.record[i]=x;
				}
		
	}
	public void print_record_values(){
			print obj = new print();
			obj.print("values for ");
			obj.print(this.attribute_name);
			obj.print(" attribute :");
			for (int i=0;i<this.number_of_records ;i++ ) {
					
					obj.print(this.record[i]);
					obj.print(" ");
				}
			obj.new_line();
	}

	public void create_bitmap(){
		for (int i=0;i<number_of_records ;++i ) {
			String bit="";
			for(int j = 0;j<number_of_records;++j){
					if(this.record[i] == this.record[j])
						bit+="1";
					else
						bit+="0";
				}
			this.bitmap[i]=bit;
			}
	}

	public void print_bitmap(){
		print obj = new print();
		obj.print("bitmap for attribute ");
		obj.print(this.attribute_name);
		obj.new_line();
		for (int i=0;i<number_of_records ;++i ) {
			obj.print(this.record[i]);
			obj.print("-------");
			obj.print(this.bitmap[i]);
			obj.new_line();
		}
	}

	public String range(int start, int end){
		print p = new print();
			String result = "";
			for (int i = 0;i < number_of_records ;i++ ) {
				result +="0";
			}
			for (int i = start ; i <=end ; i++){
				for (int j = 0; j < number_of_records; j++){
					if(i == this.record[j]){
						result = p.or(result , this.bitmap[j] , padding);
					}
				}
			}
			return result;
	}

}


class sample{
	
	public static void main(String[] args) {
		
		print obj=new print();
		Scanner inn=new Scanner(System.in);
		
		int number_of_records;
		System.out.println("Enter number of records: ");
		number_of_records=inn.nextInt();


		int number_of_attributes = 2;
		// System.out.println("Enter number of attributes: ");
		// number_of_attributes=inn.nextInt();

		attribute[] a=new attribute[2];
		
		String s;
		
		for (int i=0; i<2; i++) {
			obj.print("enter attribute name ");
			obj.print(i+1);
			obj.print(": ");
			s = inn.next();
			a[i]=new attribute(number_of_records, s);
		}

		for (int i=0;i<number_of_attributes ;i++ ) {
			a[i].scan_record_values(inn);
			obj.new_line();
		}
		
		for (int i=0;i<number_of_attributes ;i++ ) {
			a[i].print_record_values();
			obj.new_line();
		}
		
		for (int i=0;i<number_of_attributes ;i++ ) {
			a[i].create_bitmap();
		}
		for (int i=0;i<number_of_attributes ;i++ ) {
			a[i].print_bitmap();
			obj.new_line();
		}

		obj.new_line();
		obj.print("Enter start range for attribute "+a[0].attribute_name+": ");
		int x = inn.nextInt();

		obj.new_line();
		obj.print("Enter end range for attribute "+a[0].attribute_name+": ");
		int y = inn.nextInt();
		String range1 = a[0].range(x,y);

		obj.new_line();
		obj.print("Enter start range for attribute "+a[0].attribute_name+": ");
		x = inn.nextInt();

		obj.new_line();
		obj.print("Enter end range for attribute "+a[0].attribute_name+": ");
		y = inn.nextInt();		
		String range2 = a[1].range(x,y);

		String result = obj.and(range1 , range2 , a[0].padding);
		

		System.out.println("query result:");
		for (int i = 0; i < result.length() ; i++ ) {
			if(result.charAt(i) == '1'){
				System.out.println("<<" + a[0].record[i] + " , " + a[1].record[i] + " >>");
			}
		}
		
}
}