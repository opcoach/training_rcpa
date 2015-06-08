package com.opcoach.rcpa.rental.rcp;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

class LoginDialog extends Dialog
{

	private Text pw;
	private Text tl;
	private String login, passwd;

	public LoginDialog(Shell s)
	{
		super(s);
	}

	@Override
	protected Control createDialogArea(Composite parent)
	{
		Composite loginParent = new Composite(parent, SWT.NONE);
		loginParent.setLayout(new GridLayout(2, true));

		Label l = new Label(loginParent, SWT.NONE);
		l.setText("Login : ");
		tl = new Text(loginParent, SWT.NONE);
		tl.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, false));

		Label p = new Label(loginParent, SWT.NONE);
		p.setText("Password :");
		pw = new Text(loginParent, SWT.PASSWORD);
		pw.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, false));

		return loginParent;
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent)
	{
		Button button = createButton(parent, IDialogConstants.OK_ID, "Login", true);
		button.setToolTipText("Log in application");
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	@Override
	protected void okPressed()
	{
		System.out.println("Ok : Must check login " + tl + " with password : " + pw);
		passwd = pw.getText();
		login = tl.getText();
		super.okPressed();
	}

	public boolean authenticate()
	{
		if (super.open() == Dialog.OK)
		{
		  return checkLoginPassword(login, passwd);
		}
		return false;
	}

	private boolean checkLoginPassword(String l, String p)
	{
		System.out.println("Checking login : " + l + " with password " + p);
		return true;
	}
}