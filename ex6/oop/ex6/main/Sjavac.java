package oop.ex6.main;

import oop.ex6.parser.IllegalLineException;
import oop.ex6.parser.SjavaParser;

import java.io.FileNotFoundException;


public class Sjavac {
	public static void main(String[] args) {
		try {
			checkArgument(args);
			SjavaParser.parseSjavaFile(args[0]);
			System.out.println(0);
		} catch (IllegalLineException illegalLineException) {
			System.err.println(illegalLineException.getMessage());
			System.out.println(1);
		} catch (FileNotFoundException | IllegalUsageException err) {
			System.err.println(err.getMessage());
			System.out.println(2);
		}
	}

	private static void checkArgument(String[] args) throws IllegalUsageException {
		if (args.length != 1) throw new IllegalUsageException();
	}
}
