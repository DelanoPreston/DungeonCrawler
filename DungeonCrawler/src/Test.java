import Emuns.Quality;
import Generators.WeaponGenerator;

public class Test {

	public static void main(String[] args) {
		WeaponGenerator wg;
		for (int i = 0; i < 25; i++) {
			wg = new WeaponGenerator(5, Quality.Poor, Quality.Legendary);
			wg.printWeapon();
		}
		// System.out.println();
	}
	enum Size{
		Tiny, Small, Normal, Large, Huge
	}
	
	

}
