package simpleAdder.compiler;

import java.io.FileReader;
import java.io.PushbackReader;

import simpleAdder.analysis.*;
import simpleAdder.interpret.Interpret;
import simpleAdder.lexer.*;
import simpleAdder.node.*;
import simpleAdder.parser.*;

public class Compiler extends DepthFirstAdapter {
	public String output;

	public Compiler() {
		output = "";
	}

	public void caseAStatement(AStatement node) {
		String lhs = node.getLeft().getText().trim();
		String rhs = node.getRight().getText().trim();
		output = output + "	movl	$" + lhs + ", -4(%ebp)\n" + "	movl	$" + rhs
				+ ", -8(%ebp)\n" + "	movl	-8(%ebp), %eax\n"
				+ "	addl	-4(%ebp), %eax\n" + "	movl	%eax, -12(%ebp)\n"
				+ "	movl	-12(%ebp), %eax\n" + "	movl	%eax, 12(%esp)\n"
				+ "	movl	-8(%ebp), %eax\n" + "	movl	%eax, 8(%esp)\n"
				+ "	movl	-4(%ebp), %eax\n" + "	movl	%eax, 4(%esp)\n"
				+ "	movl	$LC0, (%esp)\n" + "	call	_printf\n";

	}

	public static void main(String[] args) {
		if (args.length > 0) {
			try {
				/* Form our AST */
				Lexer lexer = new Lexer(new PushbackReader(new FileReader(
						args[0]), 1024));
				Parser parser = new Parser(lexer);
				Start ast = parser.parse();

				/* Get our Interpreter going. */
				Compiler c = new Compiler();
				ast.apply(c);
				String header = "	.file	\"main.cpp\"\n	.def	___main;	.scl	2;	.type	32;	.endef\n	.section .rdata,\"dr\"\nLC0:\n	.ascii \"%d+%d=%d\\12\\0\"\n	.text\n	.align 2\n.globl _main\n	.def	_main;	.scl	2;	.type	32;	.endef\n_main:	pushl	%ebp\n	movl	%esp, %ebp\n	subl	$40, %esp\n	andl	$-16, %esp\n	movl	$0, %eax\n	addl	$15, %eax\n	addl	$15, %eax\n	shrl	$4, %eax\n	sall	$4, %eax\n	movl	%eax, -16(%ebp)\n	movl	-16(%ebp), %eax\n	call	__alloca\n	call	___main\n	movl	$1, -4(%ebp)\n	movl	$2, -8(%ebp)\n";
				String tail ="	movl	$0, %eax\n	leave\n	ret\n	.def	_printf;	.scl	2;	.type	32;	.endef";
				System.out.println(header+c.output+tail);
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			System.err.println("usage: java simpleAdder inputFile");
			System.exit(1);
		}
	}
}
