package jsr268gp.dbmanipulation;

public class Maps {
	private static final String[] wilayas = {"Adrar","Chlef","Laghouat","Oum El Bouaghi","Batna","Bejaia","Biskra","Bechar","Blida","Bouira","Tamanrasset","Tebessa","Tlemcen","Tiaret","Tizi Ouzou","Alger","Djelfa","Jijel","Setif","Saida","Skikda","Sidi Bel Abbes","Annaba","Guelma","Constantine","Medea","Mostaganem","M'Sila","Mascara","Ouargla","Oran","El Bayadh","Illizi","Bordj Bou Arreridj","Boumerdès","El Tarf","Tindouf","Tissemsilt","El Oued","Khenchela","Souk Ahras","Tipaza","Mila","Ain Defla","Naama","Ain Temouchent","Ghardaia","Relizane","Timimoun","Bordj Badji Mokhtar","Beni Abbes","Ouled Djellal","In Salah","In Guezzam","Touggourt","Djanet","El M'Ghair","El Menia"};

	public static String getwilaya(int wilayaNumber) {
		if ((wilayaNumber <= 58) && (wilayaNumber >=0)){
			return wilayas[wilayaNumber-1];
		}
		return null;
	}

}
