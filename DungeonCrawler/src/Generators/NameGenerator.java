package Generators;

import Settings.Key;

public class NameGenerator {
	private String[] nameStart = { "Sto", "Fra", "Gle", "Cru", "Si", "Kna", "Ste", "Sta", "Ro", "Jo", "To", "Tre", "Pro" };
	private String[] nameMiddle = { "do", "be", "mi", "si", "bo", "ku", "sa", "ke", "pi", "zo", "tu", "be" };
	private String[] nameEnd = { "dre", "ton", "ah", "dy", "zie", "eth", "ert", "ble", "ll", "tt", "k", "w", "te" };

	public NameGenerator() {

	}

	public String createName() {
		boolean middle = Key.random.nextInt(100) % 2 == 1;// random true or
															// false
		String name = nameStart[Key.random.nextInt(nameStart.length)];
		if (middle)
			name += nameMiddle[Key.random.nextInt(nameMiddle.length)];
		name += nameEnd[Key.random.nextInt(nameEnd.length)];
		return name;
	}
}
