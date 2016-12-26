package com.xz.other.finalized;

public class FinalizedEscapeTestCase {
	public static FinalizedEscapeTestCase caseForEscape = null;

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		System.out.println("哈哈，我已逃逸！");
		caseForEscape = this;
	}

	public static void main(String[] args) throws InterruptedException {
		System.out.println(FinalizedEscapeTestCase.caseForEscape);
		FinalizedEscapeTestCase.caseForEscape = new FinalizedEscapeTestCase();
		System.out.println(FinalizedEscapeTestCase.caseForEscape);
		FinalizedEscapeTestCase.caseForEscape = null;
		System.gc();
		Thread.sleep(100);
		System.out.println(FinalizedEscapeTestCase.caseForEscape);
		System.gc();
		Thread.sleep(100);
		System.out.println(FinalizedEscapeTestCase.caseForEscape);
	}
}
