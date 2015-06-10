package com.opcoach.rcpa.rental.ui.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.RTFTransfer;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import com.opcoach.training.rental.Customer;


public class CopyCustomer extends AbstractHandler implements IHandler
{

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException
	{
		ISelection currentSelection = HandlerUtil.getCurrentSelection(event);
		if (currentSelection instanceof IStructuredSelection)
		{ 
			Object selectedObject = ((IStructuredSelection) currentSelection).getFirstElement();
			if (selectedObject instanceof Customer)
			{
				Customer c = (Customer) selectedObject;
				IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
				MessageDialog.openInformation(window.getShell(),
						"Copy customer",
						"Copy of "  + (c.getDisplayName()));
				
				
				Clipboard clipboard = new Clipboard(Display.getCurrent());
				String textData = c.getDisplayName();
				String rtfData = "{\\rtf1\\b\\i " + textData + "}";
				TextTransfer textTransfer = TextTransfer.getInstance();
				RTFTransfer rtfTransfer = RTFTransfer.getInstance();
				Transfer[] transfers = new Transfer[]{textTransfer, rtfTransfer};
				Object[] data = new Object[]{textData, rtfData};
				clipboard.setContents(data, transfers);
				clipboard.dispose();
			}		

		}
		
		return null;
	}

}