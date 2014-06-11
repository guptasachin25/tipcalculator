package com.codepath.assignments.tipcalculator;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class TipCalculatorActivity extends Activity 
	implements OnSeekBarChangeListener, TextWatcher {

	private static final Double PERCENT_15 = 15.0;
	private static final Double PERCENT_20 = 20.0;
	private static final Double PERCENT_25 = 25.0;
	
	private static double currentTipPercentage = 0.0;
	private static int minimumSplit = 1;
	private static int numSplit = 1;
	
	private SeekBar sbSplitCounter;
	private EditText etBillingAmount;
	private TextView tvBillingAmount;
	private TextView tvTipAmount;
	private TextView tvTotalPayableAmount;
	private TextView tvProgressBar;
	private TextView tvSplitAmount;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tip_calculator);
		etBillingAmount = (EditText) findViewById(R.id.etBillingAmount);
		etBillingAmount.addTextChangedListener(this);
		tvBillingAmount = (TextView) findViewById(R.id.tvBillingAmount);
		tvTipAmount = (TextView) findViewById(R.id.tvTipAmount);
		tvTotalPayableAmount = (TextView) findViewById(R.id.tvTotalPayableAmount);
		sbSplitCounter = (SeekBar) findViewById(R.id.sbSplitCounter);
		sbSplitCounter.setOnSeekBarChangeListener(this);
		tvProgressBar = (TextView) findViewById(R.id.tvProgressBar);
		tvSplitAmount = (TextView) findViewById(R.id.tvSplitAmount);
	}
	
	private double calculateTip(double billingAmount, double tipPercent) {
		return ((billingAmount * tipPercent) / 100);
	}
	
	private double splitCalculator(double amount, int split) {
		return amount/split;
	}
	
	private void onSubmit(double tipPercent) {
		Double billingAmount;

		try {
			billingAmount = Double.parseDouble(etBillingAmount.getText().toString());
		} catch (Exception e) {
			billingAmount = 0.0;
			tvBillingAmount.setText("");
			tvTipAmount.setText("");
			tvTotalPayableAmount.setText("");
			tvSplitAmount.setText("");
			return;
		}
		
		Double tipAmount = calculateTip(billingAmount, tipPercent);
		Double totalAmount = billingAmount + tipAmount;
		Double splitAmount = 
				splitCalculator(totalAmount, Math.max(minimumSplit, numSplit));
		
		tvBillingAmount.setText(
				String.format("$ %.2f", billingAmount));
	
		tvTipAmount.setText(
				String.format("$ %.2f", tipPercent, tipAmount));
		
		tvTotalPayableAmount.setText(
				String.format("$ %.2f", totalAmount));
		
		tvSplitAmount.setText(String.format("$ %.2f", 
				splitCalculator(totalAmount, Math.max(minimumSplit, numSplit))));
	}
	
	public void onSubmit15(View v) {
		currentTipPercentage = PERCENT_15;
		onSubmit(PERCENT_15);
	}
	
	public void onSubmit20(View v) {
		currentTipPercentage = PERCENT_20;
		onSubmit(PERCENT_20);
	}

	public void onSubmit25(View v) {
		currentTipPercentage = PERCENT_25;
		onSubmit(PERCENT_25);
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		numSplit = progress;
		tvProgressBar.setText("Split: "+progress);
		onSubmit(currentTipPercentage);
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		//onSubmit(currentTipPercentage);		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		//onSubmit(currentTipPercentage);
	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		onSubmit(currentTipPercentage);
	}
}
