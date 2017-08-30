package projectEuler;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class ProjectEuler {
	static Composite exerciseLayer;
	static int page = 0;
	static Button[]  btn = new Button[10];
	static Label navCurPage;
	public static void main(String[] args) {
		Display display = new Display();
		final Shell shell = new Shell(display, SWT.SHELL_TRIM & (~SWT.RESIZE)); //Disabled resizing
		final Group exGroup = new Group(shell, SWT.SHADOW_ETCHED_IN);
		final Group problemStatementGroup = new Group(shell, SWT.SHADOW_ETCHED_IN);		
		RowLayout twoColumnsRow = new RowLayout();
		//RowData myRowData = new RowData();
		final RowLayout verticalRowLayout = new RowLayout(SWT.VERTICAL);
		
		shell.setText("ZC's Project Euler");
		shell.setLayout(twoColumnsRow);
		problemStatementGroup.setLayout(new RowLayout(SWT.VERTICAL));
		
		twoColumnsRow.type = SWT.HORIZONTAL;				
		twoColumnsRow.wrap = false;
		
		exGroup.setLayout(new GridLayout());
		exGroup.setText("Choose an exercise:");
		btn[0] = new Button(exGroup, SWT.RADIO);
		btn[1] = new Button(exGroup, SWT.RADIO);
		btn[2] = new Button(exGroup, SWT.RADIO);
		btn[3] = new Button(exGroup, SWT.RADIO);
		btn[4] = new Button(exGroup, SWT.RADIO);
		btn[5] = new Button(exGroup, SWT.RADIO);
		btn[6] = new Button(exGroup, SWT.RADIO);
		btn[7] = new Button(exGroup, SWT.RADIO);
		btn[8] = new Button(exGroup, SWT.RADIO);
		btn[9] = new Button(exGroup, SWT.RADIO);
		final Group pageNav = new Group(exGroup, SWT.NONE);
		Button navPrev = new Button(pageNav, SWT.BUTTON1);
		navPrev.setText("Prev");
		navCurPage = new Label(pageNav, SWT.NONE);
		Button navNext = new Button(pageNav, SWT.BUTTON1);
		navNext.setText("Next");
		pageNav.setLayout(new RowLayout());
		/*
		final Button  b2 = new Button(exGroup, SWT.RADIO);
		final Button  b3 = new Button(exGroup, SWT.RADIO);
		final Button  b4 = new Button(exGroup, SWT.RADIO);
		final Button  b5 = new Button(exGroup, SWT.RADIO);
		final Button  b6 = new Button(exGroup, SWT.RADIO);
		final Button  b7 = new Button(exGroup, SWT.RADIO);
		final Button  b8 = new Button(exGroup, SWT.RADIO);
		final Button  b9 = new Button(exGroup, SWT.RADIO);
		final Button b10 = new Button(exGroup, SWT.RADIO);
		*/
		updateProblemTitles();
		
		exGroup.pack();
		
		problemStatementGroup.setText("Problem Statement:");
		
		//For some reason there is a 22x56 decrease after it's actually created, probably top bar and borders but still, what the?
		shell.setMinimumSize(1411+22, 720+56);
		
		exerciseLayer = new Composite(shell, SWT.NONE);
		shell.pack();
		shell.open();

		//This listener only used to determine set size, then we disable resize when we know the optimal size.
		//Ideally the text would wrap and provide scroll bars
		/*
		shell.addListener(SWT.Resize, new Listener(){

			@Override
			public void handleEvent(Event event) {
				Rectangle rect = shell.getClientArea();
				System.out.println(rect);
			}
		});
		 */
		
		Rectangle rect = shell.getClientArea();
		System.out.println(rect);

		navPrev.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (page != 0)
				{
					page = page - 1;
					updateProblemTitles();
					pageNav.pack();
					exGroup.pack();
					exerciseLayer.dispose();
					exerciseLayer = new Composite(shell, SWT.NONE);
					shell.requestLayout();
				}
			}
		});
		navNext.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (page != 1)
				{
					page = page + 1;
					updateProblemTitles();
					pageNav.pack();
					exGroup.pack();
					exerciseLayer.dispose();
					exerciseLayer = new Composite(shell, SWT.NONE);
					shell.requestLayout();
				}
			}
		});
		
		//I'd like to make this generic, with some function pointers, but for now this works. Maybe an array of buttons would be a good compromise.
		btn[0].addSelectionListener(new SelectionAdapter() {
			boolean lastState = false;
			@Override
			public void widgetSelected(SelectionEvent e) {
				if ((btn[0].getSelection() == true) && (lastState != true))
				{
					lastState = true;
					exerciseLayer.dispose();
					if      (page == 0) exerciseLayer = createLayerP1(problemStatementGroup);
					else if (page == 1) exerciseLayer = createLayerP11(problemStatementGroup);
					exerciseLayer.setLayout(verticalRowLayout);
					problemStatementGroup.requestLayout();
					shell.requestLayout();
				}
				lastState = btn[0].getSelection();
			}
		});
		btn[1].addSelectionListener(new SelectionAdapter() {
			boolean lastState = false;
			@Override
			public void widgetSelected(SelectionEvent e) {
				if ((btn[1].getSelection() == true) && (lastState != true))
				{
					lastState = true;
					exerciseLayer.dispose();
					if      (page == 0) exerciseLayer = createLayerP2(problemStatementGroup);
					else if (page == 1) exerciseLayer = createLayerP12(problemStatementGroup);
					exerciseLayer.setLayout(verticalRowLayout);
					problemStatementGroup.requestLayout();
					shell.requestLayout();
				}
				lastState = btn[1].getSelection();
			}
		});
		btn[2].addSelectionListener(new SelectionAdapter() {
			boolean lastState = false;
			@Override
			public void widgetSelected(SelectionEvent e) {
				if ((btn[2].getSelection() == true) && (lastState != true))
				{
					lastState = true;
					exerciseLayer.dispose();
					if      (page == 0) exerciseLayer = createLayerP3(problemStatementGroup);
					else if (page == 1) exerciseLayer = createLayerP13(problemStatementGroup);
					exerciseLayer.setLayout(verticalRowLayout);
					problemStatementGroup.requestLayout();
					shell.requestLayout();
				}
				lastState = btn[2].getSelection();
			}
		});
		btn[3].addSelectionListener(new SelectionAdapter() {
			boolean lastState = false;
			@Override
			public void widgetSelected(SelectionEvent e) {
				if ((btn[3].getSelection() == true) && (lastState != true))
				{
					lastState = true;
					exerciseLayer.dispose();
					if      (page == 0) exerciseLayer = createLayerP4(problemStatementGroup);
					else if (page == 1) exerciseLayer = createLayerP14(problemStatementGroup);
					exerciseLayer.setLayout(verticalRowLayout);
					problemStatementGroup.requestLayout();
					shell.requestLayout();
				}
				lastState = btn[3].getSelection();
			}
		});
		btn[4].addSelectionListener(new SelectionAdapter() {
			boolean lastState = false;
			@Override
			public void widgetSelected(SelectionEvent e) {
				if ((btn[4].getSelection() == true) && (lastState != true))
				{
					lastState = true;
					exerciseLayer.dispose();
					if      (page == 0) exerciseLayer = createLayerP5(problemStatementGroup);
					else if (page == 1) exerciseLayer = createLayerP15(problemStatementGroup);
					exerciseLayer.setLayout(verticalRowLayout);
					problemStatementGroup.requestLayout();
					shell.requestLayout();
				}
				lastState = btn[4].getSelection();
			}
		});
		btn[5].addSelectionListener(new SelectionAdapter() {
			boolean lastState = false;
			@Override
			public void widgetSelected(SelectionEvent e) {
				if ((btn[5].getSelection() == true) && (lastState != true))
				{
					lastState = true;
					exerciseLayer.dispose();
					if      (page == 0) exerciseLayer = createLayerP6(problemStatementGroup);
					else if (page == 1) exerciseLayer = createLayerP16(problemStatementGroup);
					exerciseLayer.setLayout(verticalRowLayout);
					problemStatementGroup.requestLayout();
					shell.requestLayout();
				}
				lastState = btn[5].getSelection();
			}
		});
		btn[6].addSelectionListener(new SelectionAdapter() {
			boolean lastState = false;
			@Override
			public void widgetSelected(SelectionEvent e) {
				if ((btn[6].getSelection() == true) && (lastState != true))
				{
					lastState = true;
					exerciseLayer.dispose();
					if      (page == 0) exerciseLayer = createLayerP7(problemStatementGroup);
					else if (page == 1) exerciseLayer = createLayerP17(problemStatementGroup);
					exerciseLayer.setLayout(verticalRowLayout);
					problemStatementGroup.requestLayout();
					shell.requestLayout();
				}
				lastState = btn[6].getSelection();
			}
		});
		btn[7].addSelectionListener(new SelectionAdapter() {
			boolean lastState = false;
			@Override
			public void widgetSelected(SelectionEvent e) {
				if ((btn[7].getSelection() == true) && (lastState != true))
				{
					lastState = true;
					exerciseLayer.dispose();
					if      (page == 0) exerciseLayer = createLayerP8(problemStatementGroup);
					else if (page == 1) exerciseLayer = createLayerP18(problemStatementGroup);
					exerciseLayer.setLayout(verticalRowLayout);
					problemStatementGroup.requestLayout();
					shell.requestLayout();
				}
				lastState = btn[7].getSelection();
			}
		});
		btn[8].addSelectionListener(new SelectionAdapter() {
			boolean lastState = false;
			@Override
			public void widgetSelected(SelectionEvent e) {
				if ((btn[8].getSelection() == true) && (lastState != true))
				{
					lastState = true;
					exerciseLayer.dispose();
					if      (page == 0) exerciseLayer = createLayerP9(problemStatementGroup);
					else if (page == 1) exerciseLayer = createLayerP19(problemStatementGroup);
					exerciseLayer.setLayout(verticalRowLayout);
					problemStatementGroup.requestLayout();
					shell.requestLayout();
				}
				lastState = btn[8].getSelection();
			}
		});
		btn[9].addSelectionListener(new SelectionAdapter() {
			boolean lastState = false;
			@Override
			public void widgetSelected(SelectionEvent e) {
				if ((btn[9].getSelection() == true) && (lastState != true))
				{
					lastState = true;
					exerciseLayer.dispose();
					if      (page == 0) exerciseLayer = createLayerP10(problemStatementGroup);
					else if (page == 1) exerciseLayer = createLayerP20(problemStatementGroup);
					exerciseLayer.setLayout(verticalRowLayout);
					problemStatementGroup.requestLayout();
					shell.requestLayout();
				}
				lastState = btn[9].getSelection();
			}
		});

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) display.sleep();
		}
		display.dispose();
	}
	
    protected static void updateProblemTitles() {
    	String text = new String();
		if(page == 0)
		{
			btn[0].setText("Multiples of 3 and 5");
			btn[1].setText("Even Fibonacci numbers");
			btn[2].setText("Largest prime factor");
			btn[3].setText("Largest palindrome product");
			btn[4].setText("Smallest multiple");
			btn[5].setText("Sum square difference");
			btn[6].setText("10001st prime");
			btn[7].setText("Largest product in a series");
			btn[8].setText("Special Pythagorean triplet");
			btn[9].setText("Summation of primes");
		}
		if(page == 1)
		{
			btn[0].setText("Largest product in a grid");
			btn[1].setText("Highly divisible triangular number");
			btn[2].setText("Large sum");
			btn[3].setText("Longest Collatz sequence");
			btn[4].setText("Lattice paths");
			btn[5].setText("Power digit sum");
			btn[6].setText("Number letter counts");
			btn[7].setText("Maximum path sum I");
			btn[8].setText("Counting Sundays");
			btn[9].setText("Factorial digit sum");
		}
		text = String.format("Page %d of %d", page+1, 2);
		navCurPage.setText(text);
	}

	private static Composite createLayerP1(final Composite parent) {

        final Composite layer = new Composite(parent, SWT.NONE);
        
        //put widgets in this layer
        final Label problemText = new Label(layer, SWT.NONE);
        final Button calculate = new Button(layer, SWT.PUSH);
        final Label answer = new Label(layer, SWT.BORDER);

        problemText.setText("If we list all the natural numbers below 10 that are multiples of 3 or 5, we get 3, 5, 6 and 9. The sum of these multiples is 23.\n"
        		+ "\n"
        		+ "Find the sum of all the multiples of 3 or 5 below 1000.");
        
        calculate.setText("calculate");
        answer.setText("Answer goes here...");
        
        calculate.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
            	int sum = 0;
            	int i = 3;
            	for (i = 3; i < 1000; i++)
            	{
            		if (((i%3) == 0) || ((i%5) == 0))
            		{
            			sum = sum + i;
            		}
            	}
            	String result = String.format("%d", sum);
            	answer.setText(result);
            	answer.pack();
            }
        });
        
        layer.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				problemText.dispose();
		        calculate.dispose();        
		        answer.dispose();
		        System.out.printf("Disposing of P1 layer\n");
			}
		});
        
        problemText.pack();
        calculate.pack();        
        answer.pack();
        layer.pack();
		
        return layer;
    }
	private static Composite createLayerP2(final Composite parent) {
		
	    final Composite layer = new Composite(parent, SWT.NONE);
	    //put widgets in this layer
        final Label problemText = new Label(layer, SWT.NONE);
        final Button calculate = new Button(layer, SWT.PUSH);
        final Label answer = new Label(layer, SWT.BORDER);

        problemText.setText("Each new term in the Fibonacci sequence is generated by adding the previous two terms. By starting with 1 and 2, the first 10 terms will be:\r"
	    		+ "\r"
	    		+ "1, 2, 3, 5, 8, 13, 21, 34, 55, 89, ...\r"
	    		+ "\r"
	    		+ "By considering the terms in the Fibonacci sequence whose values do not exceed four million, find the sum of the even-valued terms.");
	    
        calculate.setText("calculate");
        answer.setText("Answer goes here...");
        
        calculate.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
            	long result = 0;
            	int a = 1;
            	int b = 1;
            	int swap;
            	while(b < 4000000)
            	{
            		swap = a+b;
            		a = b;
            		b = swap;
            		if (b%2 == 0)
            		{
            			result = result + b;
            		}
            	}
            	String sresult = String.format("%d", result);
            	answer.setText(sresult);
            	answer.pack();
            }
        });
        
        layer.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				problemText.dispose();
		        calculate.dispose();        
		        answer.dispose();
			}
		});
        
        problemText.pack();
        calculate.pack();        
        answer.pack();
        layer.pack();
		
        return layer;
	}
	private static Composite createLayerP3(final Composite parent) {
		
	    final Composite layer = new Composite(parent, SWT.NONE);
	    //put widgets in this layer
        final Label problemText = new Label(layer, SWT.NONE);
        final Button calculate = new Button(layer, SWT.PUSH);
        final Label answer = new Label(layer, SWT.BORDER);

        problemText.setText("The prime factors of 13195 are 5, 7, 13 and 29.\r"
        		+ "\r"
        		+ "What is the largest prime factor of the number 600851475143 ?");
        calculate.setText("calculate");
        answer.setText("Answer goes here...");

        calculate.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
            	long input = 600851475143L;
            	//long input = 13195L;
            	long inputFactored;
            	long i = 3;
            	String sresult = "";
            	long uppermost = (long) Math.sqrt(input);
            	System.out.printf("square root = %d\n", uppermost);
            	for (i = 2; i < uppermost; i++)
            	{
            		if (isPrime(i))
            		{
            			//System.out.printf("%d is prime.\n", i);
            			if (input % i == 0)
            			{
            				inputFactored = input / i;
            				uppermost = (long) Math.sqrt(Math.floor(inputFactored));
            				System.out.printf("new factor=%d, inputFactored=%d, uppermost=%d\n", i, inputFactored, uppermost);
            				if (sresult.length() == 0)
            				{
            					sresult = String.format("%d", i);            					
            				}
            				else
            				{
            					sresult = String.format("%s %d", sresult, i);
            				}
            				System.out.println(sresult);
            			}
            		}
            	}
            	answer.setText(sresult);
            	answer.pack();
            }
        });
        
        layer.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				problemText.dispose();
		        calculate.dispose();        
		        answer.dispose();
			}
		});
        
        problemText.pack();
        calculate.pack();        
        answer.pack();
        layer.pack();
		
        return layer;
	}
	private static Composite createLayerP4(final Composite parent) {
		
	    final Composite layer = new Composite(parent, SWT.NONE);
	    //put widgets in this layer
        final Label problemText = new Label(layer, SWT.NONE);
        final Button calculate = new Button(layer, SWT.PUSH);
        final Label answer = new Label(layer, SWT.BORDER);

        problemText.setText("A palindromic number reads the same both ways. The largest palindrome made from the product of two 2-digit numbers is 9009 = 91 × 99.\n"
        		+ "\n"
        		+ "Find the largest palindrome made from the product of two 3-digit numbers.");
        calculate.setText("calculate");
        answer.setText("Answer goes here...");

        calculate.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
            	String sresult = "";
            	//Max is 998001
            	//Min is  10000
            	int product;
            	int maxProduct = 0;
            	for(int i = 100; i < 1000; i++)
            	{
            		for(int j = 100; j < 1000; j++)
            		{
            			product = i * j;
            			if (product == reverse(product))
            			{
            				//System.out.printf("%d * %d = %d\n", i, j, product);
            				if (product > maxProduct) maxProduct = product;
            			}
            		}
            	}            	
				sresult = String.format("%d", maxProduct);
            	answer.setText(sresult);
            	answer.pack();
            }
        });
        
        layer.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				problemText.dispose();
		        calculate.dispose();        
		        answer.dispose();
			}
		});
        
        problemText.pack();
        calculate.pack();        
        answer.pack();
        layer.pack();
		
        return layer;
	}
	private static Composite createLayerP5(final Composite parent) {
		
	    final Composite layer = new Composite(parent, SWT.NONE);
	    //put widgets in this layer
	    final Label problemText = new Label(layer, SWT.NONE);
	    final Button calculate = new Button(layer, SWT.PUSH);
	    final Label answer = new Label(layer, SWT.BORDER);

	    problemText.setText("2520 is the smallest number that can be divided by each of the numbers from 1 to 10 without any remainder.\n"
	    		+ "\n"
	    		+ "What is the smallest positive number that is evenly divisible by all of the numbers from 1 to 20?");
	    calculate.setText("calculate");
	    answer.setText("Answer goes here...");

	    calculate.addSelectionListener(new SelectionAdapter() {
	        @Override
	        public void widgetSelected(SelectionEvent e) {
	        	String sresult = "";
	        	int test = 64;
	        	long result = 0;
	        	int i;
	        	int j = 0;
	        	
	        	/* Original answer:
	        	 * while (result == 0)

	        	{
	        		for (i = 2; i <= 20; i++)
	        		{
	        			if (test % i != 0)
	        			{
	        				test+=2;
	        				break;
	        			}
	        		}
	        		if (i == 21)
	        		{
	        			result = test;
	        			break;
	        		}
	        	}
	        	 */
	        	//result = 2 * 3 * 2 * 5 * 7 * 2 * 3 * 11 * 13 * 2 * 17 * 19;
	        	//result = 2^4 + 3^2 + 5 + 7 + ....
	        	result = 1;
	        	for (i = 2; i < test; i++)
	        	{
	        		if (isPrime(i))
	        		{
		        		j = 0;
		        		while (Math.pow(i,j+1) <= test)
		        		{
		        			j++;
		        		}
			        	result = result * (long)Math.pow(i,j);
			        	System.out.printf("i=%d, j=%d\n", i, j);
	        		}
	        	}
	        	sresult = String.format("%d", result);
	        	answer.setText(sresult);
	        	answer.pack();
	        }
	    });
	    
	    layer.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				problemText.dispose();
		        calculate.dispose();        
		        answer.dispose();
			}
		});
	    
	    problemText.pack();
	    calculate.pack();        
	    answer.pack();
	    layer.pack();
		
	    return layer;
	}
	private static Composite createLayerP6(final Composite parent) {
		
	    final Composite layer = new Composite(parent, SWT.NONE);
	    //put widgets in this layer
	    final Label problemText = new Label(layer, SWT.NONE);
	    final Button calculate = new Button(layer, SWT.PUSH);
	    final Label answer = new Label(layer, SWT.BORDER);

	    problemText.setText("The sum of the squares of the first ten natural numbers is:\n"
	    		+ "1^2 + 2^2 + ... + 1=^2 = 385\n"
	    		+ "The square of the sum of the first ten natural numbers is:\n"
	    		+ "(1 + 2 + ... + 10)^2 = 55^2 = 3025\n"
	    		+ "Hence the difference between the sum of the squares of the first ten natural numbers and the square of the sum is 3025 - 385 = 2640.\n"
	    		+ "Find the difference between the sum of the squares of the first one hundred natural numbers and the square of the sum.");
	    calculate.setText("calculate");
	    answer.setText("Answer goes here...");

	    calculate.addSelectionListener(new SelectionAdapter() {
	        @Override
	        public void widgetSelected(SelectionEvent e) {
	        	long result = 0;
	        	int sum = 0;
	        	long squareOfSum = 0;
	        	long sumOfSquares = 0;
	        	String sresult = "";

	        	for (int i = 1; i <= 100; i++)
	        	{
	        		sum = sum + i;
	        		sumOfSquares = sumOfSquares + (long)Math.pow((double)i, (double)2);
	        	}
	        	
	        	squareOfSum = (long)Math.pow((double)sum, (double)2);
	        	System.out.printf("square of sum = %d\n", squareOfSum);
	        	System.out.printf("sum of squares = %d\n", sumOfSquares);
	        	result = squareOfSum - sumOfSquares;
	        	
	        	sresult = String.format("%d", result);
	        	answer.setText(sresult);
	        	answer.pack();
	        }
	    });
	    
	    layer.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				problemText.dispose();
		        calculate.dispose();        
		        answer.dispose();
			}
		});
	    
	    problemText.pack();
	    calculate.pack();        
	    answer.pack();
	    layer.pack();
		
	    return layer;
	}
	private static Composite createLayerP7(final Composite parent) {
		
	    final Composite layer = new Composite(parent, SWT.NONE);
	    //put widgets in this layer
	    final Label problemText = new Label(layer, SWT.NONE);
	    final Button calculate = new Button(layer, SWT.PUSH);
	    final Label answer = new Label(layer, SWT.BORDER);

	    problemText.setText("By listing the first six prime numbers: 2, 3, 5, 7, 11, and 13, we can see that the 6th prime is 13.\n"
	    		+ "\n"
	    		+ "What is the 10 001st prime number?");
	    calculate.setText("calculate");
	    answer.setText("Answer goes here...");

	    calculate.addSelectionListener(new SelectionAdapter() {
	        @Override
	        public void widgetSelected(SelectionEvent e) {
	        	long result = 0;
	        	String sresult = "";
	        	int nPrimes = 0;
	        	long i = 2;
	        	
	        	while (nPrimes < 10001)
	        	{
	        		if (isPrime(i))
	        		{
	        			nPrimes++;
	        			System.out.printf("Prime #%d is %d\n", nPrimes, i);
	        		}
	        		i++;
	        	}
	        	result = i - 1;

	        	sresult = String.format("%d", result);
	        	answer.setText(sresult);
	        	answer.pack();
	        }
	    });
	    
	    layer.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				problemText.dispose();
		        calculate.dispose();        
		        answer.dispose();
			}
		});
	    
	    problemText.pack();
	    calculate.pack();        
	    answer.pack();
	    layer.pack();
		
	    return layer;
	}
	private static Composite createLayerP8(final Composite parent) {
		
	    final Composite layer = new Composite(parent, SWT.NONE);
	    //put widgets in this layer
	    final Label problemText = new Label(layer, SWT.NONE);
	    final Button calculate = new Button(layer, SWT.PUSH);
	    final Label answer = new Label(layer, SWT.BORDER);

	    problemText.setText("The four adjacent digits in the 1000-digit number that have the greatest product are 9 × 9 × 8 × 9 = 5832.\n"
	    		+ "\n"
	    		+ "73167176531330624919225119674426574742355349194934\n"
	    		+ "96983520312774506326239578318016984801869478851843\n"
	    		+ "85861560789112949495459501737958331952853208805511\n"
	    		+ "12540698747158523863050715693290963295227443043557\n"
	    		+ "66896648950445244523161731856403098711121722383113\n"
	    		+ "62229893423380308135336276614282806444486645238749\n"
	    		+ "30358907296290491560440772390713810515859307960866\n"
	    		+ "70172427121883998797908792274921901699720888093776\n"
	    		+ "65727333001053367881220235421809751254540594752243\n"
	    		+ "52584907711670556013604839586446706324415722155397\n"
	    		+ "53697817977846174064955149290862569321978468622482\n"
	    		+ "83972241375657056057490261407972968652414535100474\n"
	    		+ "82166370484403199890008895243450658541227588666881\n"
	    		+ "16427171479924442928230863465674813919123162824586\n"
	    		+ "17866458359124566529476545682848912883142607690042\n"
	    		+ "24219022671055626321111109370544217506941658960408\n"
	    		+ "07198403850962455444362981230987879927244284909188\n"
	    		+ "84580156166097919133875499200524063689912560717606\n"
	    		+ "05886116467109405077541002256983155200055935729725\n"
	    		+ "71636269561882670428252483600823257530420752963450\n"
	    		+ "\n"
	    		+ "Find the thirteen adjacent digits in the 1000-digit number that have the greatest product. What is the value of this product?");
	    calculate.setText("calculate");
	    answer.setText("Answer goes here...");

	    calculate.addSelectionListener(new SelectionAdapter() {
	        @Override
	        public void widgetSelected(SelectionEvent e) {
	        	long result = 0;
	        	String sresult = "";
	        	String testString = "73167176531330624919225119674426574742355349194934"
	            		+ "96983520312774506326239578318016984801869478851843"
	            		+ "85861560789112949495459501737958331952853208805511"
	            		+ "12540698747158523863050715693290963295227443043557"
	            		+ "66896648950445244523161731856403098711121722383113"
	            		+ "62229893423380308135336276614282806444486645238749"
	            		+ "30358907296290491560440772390713810515859307960866"
	            		+ "70172427121883998797908792274921901699720888093776"
	            		+ "65727333001053367881220235421809751254540594752243"
	            		+ "52584907711670556013604839586446706324415722155397"
	            		+ "53697817977846174064955149290862569321978468622482"
	            		+ "83972241375657056057490261407972968652414535100474"
	            		+ "82166370484403199890008895243450658541227588666881"
	            		+ "16427171479924442928230863465674813919123162824586"
	            		+ "17866458359124566529476545682848912883142607690042"
	            		+ "24219022671055626321111109370544217506941658960408"
	            		+ "07198403850962455444362981230987879927244284909188"
	            		+ "84580156166097919133875499200524063689912560717606"
	            		+ "05886116467109405077541002256983155200055935729725"
	            		+ "71636269561882670428252483600823257530420752963450";
	        	int startPos = 0;
	        	long product = 0;
	        	long maxProduct = 0;
	        	int i;
	        	String substring = "";
	        	
	        	//System.out.printf("%s\n", testString);
	        	
	        	for (startPos = 0; startPos < testString.length()-13; startPos++)
	        	{
	        		product = 1;
	        		substring = testString.substring(startPos, startPos + 13);
	        		//System.out.printf("Substring = %s\n", substring);
	        		for (i = 0; i < 13; i++)
	        		{
	        			product = product * Character.getNumericValue(substring.charAt(i));
	        		}
	        		if (product > maxProduct)
	        		{
	        			System.out.printf("product %d is larger than previous max of %d\n", product, maxProduct);
	        			System.out.printf("Substring = %s, i=%d, startPos=%d\n", testString.substring(startPos, startPos + 13), i, startPos);
	        			maxProduct = product;
	        		}
	        	}
	        	result = maxProduct;
	        	
	        	sresult = String.format("%d", result);
	        	answer.setText(sresult);
	        	answer.pack();
	        }
	    });
    
    layer.addDisposeListener(new DisposeListener() {
		@Override
		public void widgetDisposed(DisposeEvent e) {
			problemText.dispose();
	        calculate.dispose();        
	        answer.dispose();
		}
	});
    
    problemText.pack();
    calculate.pack();        
    answer.pack();
    layer.pack();
	
    return layer;
}
	private static Composite createLayerP9(final Composite parent) {
		
	    final Composite layer = new Composite(parent, SWT.NONE);
	    //put widgets in this layer
	    final Label problemText = new Label(layer, SWT.NONE);
	    final Button calculate = new Button(layer, SWT.PUSH);
	    final Label answer = new Label(layer, SWT.BORDER);

	    problemText.setText("A Pythagorean triplet is a set of three natural numbers, a < b < c, for which,\n"
	    		+ "a2 + b2 = c2\n"
	    		+ "For example, 32 + 42 = 9 + 16 = 25 = 52.\n"
	    		+ "There exists exactly one Pythagorean triplet for which a + b + c = 1000.\n"
	    		+ "Find the product abc.");
	    calculate.setText("calculate");
	    answer.setText("Answer goes here...");

	    calculate.addSelectionListener(new SelectionAdapter() {
	        @Override
	        public void widgetSelected(SelectionEvent e) {
	        	long result = 0;
	        	String sresult = "";
	        	int a=1;
	        	int b=1;
	        	int c=1;
	        	int sum;
	        	
	        	for (a=1; a<1000; a++)
	        	{
    				sum = a+a+1+a+2;
	        		for (b=a+1; b<1001-a; b++)
	        		{
        				sum = a+b+b+1;
	        			for (c=b+1; c<(1001-(a+b)); c++)
	        			{
	        				sum = a+b+c;
	        				if (sum == 1000)
	        				{
	        					if (isTriplet(a, b, c))
	        					{
	        						result = a*b*c;
	        					}
	        				}
	        			}
	        		}
	        	}
	        	
	        	sresult = String.format("%d", result);
	        	answer.setText(sresult);
	        	answer.pack();
	        }
	    });
    
	    layer.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				problemText.dispose();
		        calculate.dispose();        
		        answer.dispose();
			}
		});
	    
	    problemText.pack();
	    calculate.pack();        
	    answer.pack();
	    layer.pack();
		
	    return layer;
	}
	private static Composite createLayerP10(final Composite parent) {
	
	    final Composite layer = new Composite(parent, SWT.NONE);
	    //put widgets in this layer
	    final Label problemText = new Label(layer, SWT.NONE);
	    final Button calculate = new Button(layer, SWT.PUSH);
	    final Label answer = new Label(layer, SWT.BORDER);
	
	    problemText.setText("The sum of the primes below 10 is 2 + 3 + 5 + 7 = 17.\n"
	    		+ "Find the sum of all the primes below two million.");
	    calculate.setText("calculate");
	    answer.setText("Answer goes here...");
	
	    calculate.addSelectionListener(new SelectionAdapter() {
	        @Override
	        public void widgetSelected(SelectionEvent e) {
	        	long result = 0;
	        	String sresult = "";
	        	long sum = 2;
	        	
	        	for (int i = 3; i < 2000000; i+=2)
	        	{
	        		if (isPrime(i))
	        		{
	        			sum = sum + i;
	        			//System.out.printf("With the addition of %d, sum=%d\n", i, sum);
	        		}
	        	}
	        	result = sum;
	        	
	        	sresult = String.format("%d", result);
	        	answer.setText(sresult);
	        	answer.pack();
	        }
	    });
	
		layer.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				problemText.dispose();
		        calculate.dispose();        
		        answer.dispose();
			}
		});
		
		problemText.pack();
		calculate.pack();        
		answer.pack();
		layer.pack();
		
		return layer;
	}
	private static Composite createLayerP11(final Composite parent) {
		
	    final Composite layer = new Composite(parent, SWT.NONE);
	    //put widgets in this layer
	    final Label problemText = new Label(layer, SWT.NONE);
	    final Button calculate = new Button(layer, SWT.PUSH);
	    final Label answer = new Label(layer, SWT.BORDER);

	    //TODO: insert problem statement
	    problemText.setText("P11 Insert problem statement here");
	    calculate.setText("calculate");
	    answer.setText("Answer goes here...");

	    calculate.addSelectionListener(new SelectionAdapter() {
	        @Override
	        public void widgetSelected(SelectionEvent e) {
	        	long result = 0;
	        	String sresult = "";
	        	
	        	//TODO: solve the problem!
	        	
	        	sresult = String.format("%d", result);
	        	answer.setText(sresult);
	        	answer.pack();
	        }
	    });

		layer.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				problemText.dispose();
		        calculate.dispose();        
		        answer.dispose();
			}
		});
	
		problemText.pack();
		calculate.pack();        
		answer.pack();
		layer.pack();
		
		return layer;
	}
	private static Composite createLayerP12(final Composite parent) {
		
	    final Composite layer = new Composite(parent, SWT.NONE);
	    //put widgets in this layer
	    final Label problemText = new Label(layer, SWT.NONE);
	    final Button calculate = new Button(layer, SWT.PUSH);
	    final Label answer = new Label(layer, SWT.BORDER);

	    //TODO: insert problem statement
	    problemText.setText("P12 Insert problem statement here");
	    calculate.setText("calculate");
	    answer.setText("Answer goes here...");

	    calculate.addSelectionListener(new SelectionAdapter() {
	        @Override
	        public void widgetSelected(SelectionEvent e) {
	        	long result = 0;
	        	String sresult = "";
	        	
	        	//TODO: solve the problem!
	        	
	        	sresult = String.format("%d", result);
	        	answer.setText(sresult);
	        	answer.pack();
	        }
	    });

		layer.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				problemText.dispose();
		        calculate.dispose();        
		        answer.dispose();
			}
		});
	
		problemText.pack();
		calculate.pack();        
		answer.pack();
		layer.pack();
		
		return layer;
	}
	private static Composite createLayerP13(final Composite parent) {
		
	    final Composite layer = new Composite(parent, SWT.NONE);
	    //put widgets in this layer
	    final Label problemText = new Label(layer, SWT.NONE);
	    final Button calculate = new Button(layer, SWT.PUSH);
	    final Label answer = new Label(layer, SWT.BORDER);

	    //TODO: insert problem statement
	    problemText.setText("P13 Insert problem statement here");
	    calculate.setText("calculate");
	    answer.setText("Answer goes here...");

	    calculate.addSelectionListener(new SelectionAdapter() {
	        @Override
	        public void widgetSelected(SelectionEvent e) {
	        	long result = 0;
	        	String sresult = "";
	        	
	        	//TODO: solve the problem!
	        	
	        	sresult = String.format("%d", result);
	        	answer.setText(sresult);
	        	answer.pack();
	        }
	    });

		layer.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				problemText.dispose();
		        calculate.dispose();        
		        answer.dispose();
			}
		});
	
		problemText.pack();
		calculate.pack();        
		answer.pack();
		layer.pack();
		
		return layer;
	}
	private static Composite createLayerP14(final Composite parent) {
		
	    final Composite layer = new Composite(parent, SWT.NONE);
	    //put widgets in this layer
	    final Label problemText = new Label(layer, SWT.NONE);
	    final Button calculate = new Button(layer, SWT.PUSH);
	    final Label answer = new Label(layer, SWT.BORDER);

	    //TODO: insert problem statement
	    problemText.setText("P14 Insert problem statement here");
	    calculate.setText("calculate");
	    answer.setText("Answer goes here...");

	    calculate.addSelectionListener(new SelectionAdapter() {
	        @Override
	        public void widgetSelected(SelectionEvent e) {
	        	long result = 0;
	        	String sresult = "";
	        	
	        	//TODO: solve the problem!
	        	
	        	sresult = String.format("%d", result);
	        	answer.setText(sresult);
	        	answer.pack();
	        }
	    });

		layer.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				problemText.dispose();
		        calculate.dispose();        
		        answer.dispose();
			}
		});
	
		problemText.pack();
		calculate.pack();        
		answer.pack();
		layer.pack();
		
		return layer;
	}
	private static Composite createLayerP15(final Composite parent) {
		
	    final Composite layer = new Composite(parent, SWT.NONE);
	    //put widgets in this layer
	    final Label problemText = new Label(layer, SWT.NONE);
	    final Button calculate = new Button(layer, SWT.PUSH);
	    final Label answer = new Label(layer, SWT.BORDER);

	    //TODO: insert problem statement
	    problemText.setText("P15 Insert problem statement here");
	    calculate.setText("calculate");
	    answer.setText("Answer goes here...");

	    calculate.addSelectionListener(new SelectionAdapter() {
	        @Override
	        public void widgetSelected(SelectionEvent e) {
	        	long result = 0;
	        	String sresult = "";
	        	
	        	//TODO: solve the problem!
	        	
	        	sresult = String.format("%d", result);
	        	answer.setText(sresult);
	        	answer.pack();
	        }
	    });

		layer.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				problemText.dispose();
		        calculate.dispose();        
		        answer.dispose();
			}
		});
	
		problemText.pack();
		calculate.pack();        
		answer.pack();
		layer.pack();
		
		return layer;
	}
	private static Composite createLayerP16(final Composite parent) {
		
	    final Composite layer = new Composite(parent, SWT.NONE);
	    //put widgets in this layer
	    final Label problemText = new Label(layer, SWT.NONE);
	    final Button calculate = new Button(layer, SWT.PUSH);
	    final Label answer = new Label(layer, SWT.BORDER);

	    //TODO: insert problem statement
	    problemText.setText("P16 Insert problem statement here");
	    calculate.setText("calculate");
	    answer.setText("Answer goes here...");

	    calculate.addSelectionListener(new SelectionAdapter() {
	        @Override
	        public void widgetSelected(SelectionEvent e) {
	        	long result = 0;
	        	String sresult = "";
	        	
	        	//TODO: solve the problem!
	        	
	        	sresult = String.format("%d", result);
	        	answer.setText(sresult);
	        	answer.pack();
	        }
	    });

		layer.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				problemText.dispose();
		        calculate.dispose();        
		        answer.dispose();
			}
		});
	
		problemText.pack();
		calculate.pack();        
		answer.pack();
		layer.pack();
		
		return layer;
	}
	private static Composite createLayerP17(final Composite parent) {
		
	    final Composite layer = new Composite(parent, SWT.NONE);
	    //put widgets in this layer
	    final Label problemText = new Label(layer, SWT.NONE);
	    final Button calculate = new Button(layer, SWT.PUSH);
	    final Label answer = new Label(layer, SWT.BORDER);

	    //TODO: insert problem statement
	    problemText.setText("P17 Insert problem statement here");
	    calculate.setText("calculate");
	    answer.setText("Answer goes here...");

	    calculate.addSelectionListener(new SelectionAdapter() {
	        @Override
	        public void widgetSelected(SelectionEvent e) {
	        	long result = 0;
	        	String sresult = "";
	        	
	        	//TODO: solve the problem!
	        	
	        	sresult = String.format("%d", result);
	        	answer.setText(sresult);
	        	answer.pack();
	        }
	    });

		layer.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				problemText.dispose();
		        calculate.dispose();        
		        answer.dispose();
			}
		});
	
		problemText.pack();
		calculate.pack();        
		answer.pack();
		layer.pack();
		
		return layer;
	}
	private static Composite createLayerP18(final Composite parent) {
		
	    final Composite layer = new Composite(parent, SWT.NONE);
	    //put widgets in this layer
	    final Label problemText = new Label(layer, SWT.NONE);
	    final Button calculate = new Button(layer, SWT.PUSH);
	    final Label answer = new Label(layer, SWT.BORDER);

	    //TODO: insert problem statement
	    problemText.setText("P18 Insert problem statement here");
	    calculate.setText("calculate");
	    answer.setText("Answer goes here...");

	    calculate.addSelectionListener(new SelectionAdapter() {
	        @Override
	        public void widgetSelected(SelectionEvent e) {
	        	long result = 0;
	        	String sresult = "";
	        	
	        	//TODO: solve the problem!
	        	
	        	sresult = String.format("%d", result);
	        	answer.setText(sresult);
	        	answer.pack();
	        }
	    });

		layer.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				problemText.dispose();
		        calculate.dispose();        
		        answer.dispose();
			}
		});
	
		problemText.pack();
		calculate.pack();        
		answer.pack();
		layer.pack();
		
		return layer;
	}
	private static Composite createLayerP19(final Composite parent) {
		
	    final Composite layer = new Composite(parent, SWT.NONE);
	    //put widgets in this layer
	    final Label problemText = new Label(layer, SWT.NONE);
	    final Button calculate = new Button(layer, SWT.PUSH);
	    final Label answer = new Label(layer, SWT.BORDER);

	    //TODO: insert problem statement
	    problemText.setText("P19 Insert problem statement here");
	    calculate.setText("calculate");
	    answer.setText("Answer goes here...");

	    calculate.addSelectionListener(new SelectionAdapter() {
	        @Override
	        public void widgetSelected(SelectionEvent e) {
	        	long result = 0;
	        	String sresult = "";
	        	
	        	//TODO: solve the problem!
	        	
	        	sresult = String.format("%d", result);
	        	answer.setText(sresult);
	        	answer.pack();
	        }
	    });

		layer.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				problemText.dispose();
		        calculate.dispose();        
		        answer.dispose();
			}
		});
	
		problemText.pack();
		calculate.pack();        
		answer.pack();
		layer.pack();
		
		return layer;
	}
	private static Composite createLayerP20(final Composite parent) {
		
	    final Composite layer = new Composite(parent, SWT.NONE);
	    //put widgets in this layer
	    final Label problemText = new Label(layer, SWT.NONE);
	    final Button calculate = new Button(layer, SWT.PUSH);
	    final Label answer = new Label(layer, SWT.BORDER);

	    //TODO: insert problem statement
	    problemText.setText("P20 Insert problem statement here");
	    calculate.setText("calculate");
	    answer.setText("Answer goes here...");

	    calculate.addSelectionListener(new SelectionAdapter() {
	        @Override
	        public void widgetSelected(SelectionEvent e) {
	        	long result = 0;
	        	String sresult = "";
	        	
	        	//TODO: solve the problem!
	        	
	        	sresult = String.format("%d", result);
	        	answer.setText(sresult);
	        	answer.pack();
	        }
	    });

		layer.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				problemText.dispose();
		        calculate.dispose();        
		        answer.dispose();
			}
		});
	
		problemText.pack();
		calculate.pack();        
		answer.pack();
		layer.pack();
		
		return layer;
	}

	protected static boolean isTriplet(int a, int b, int c) {
		//a < b < c
		if (Math.pow(a,2) + Math.pow(b,2) == Math.pow(c,2)) return true;
		return false;
	}
	protected static int reverse(int i) {
		String input = new String();
		String reversed = new String();
		
		input = String.format("%d", i);
		reversed = new StringBuilder(input).reverse().toString();
		
		int j = Integer.parseInt(reversed);
		return j;
	}
	protected static boolean isPrimeOld(long input) {
		long i;
		
		if (input%2 == 0)
		{
			if (input==2) return true;
			return false;
		}
		for(i=3; i<=(input/2); i+=2)
		{
			if(input%i == 0) return false;
		}
		return true;
	}
	protected static boolean isPrime(long n) {
		int root;
		int j;
		
		if (n == 1) return false;
		if (n < 4) return true; //2 and 3 are prime
		if (n % 2 == 0) return false;
		if (n < 9) return true; //we have already excluded 4, 6 and 8.
		if (n % 3 == 0) return false;
		root = (int) Math.floor( Math.sqrt(n) );
		j = 5;
		while (j <= root)
		{
			//Only 2 cases out of 6 that aren't multiples of 2 or 3, which were already excluded
			if (n % j == 0) return false; //a multiple of 2, +1.  Also a multiple of 3, +2
			if (n % (j+2) == 0) return false; //a multiple of 2, +3.  Also a multiple of 3, +1
			j = j+6;
		}
		/* maybe something like this would improve performance, but it's not quite right.
		if (n % 5 == 0) return false;
		j = 7;
		while (j <= root)
		{
			//only x cases out of 30 that aren't multiples of 2, 3, or 5
			if (n%j == 0)    return false; //x*2+1, y*3+1, z*5+2 //7
			if (n%j+4 == 0)  return false; //x*2+1, y*3+2, z*5+1 //11
			if (n%j+6 == 0)  return false; //x*2+1, y*3+1, z*5+3 //13
			if (n%j+10 == 0) return false; //x*2+1, y*3+2, z*5+2 //17
			if (n%j+12 == 0) return false; //x*2+1, y*3+1, z*5+4 //19
			if (n%j+16 == 0) return false; //x*2+1, y*3+2, z*5+3 //23
			if (n%j+22 == 0) return false; //x*2+1, y*3+2, z*5+4 //29
			if (n%j+24 == 0) return false; //x*2+1, y*3+1, z*5+1 //31
			j = j+30;
		}
		*/
		return true;
	}
}
