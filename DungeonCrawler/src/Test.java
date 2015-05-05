import Emuns.Quality;
import Generators.WeaponGenerator;
import Settings.Key;

public class Test {

	public static void main(String[] args) {
		WeaponGenerator wg;
		for (int i = 0; i < 25; i++) {
			wg = new WeaponGenerator(5, Quality.Poor, Quality.Legendary);
			wg.printWeapon();
		}
		// System.out.println();
	}

}
