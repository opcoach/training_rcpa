package com.opcoach.rcpa.rental.ui.views;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.RTFTransfer;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.URLTransfer;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import com.opcoach.rcpa.rental.core.RentalCoreActivator;
import com.opcoach.rcpa.rental.ui.RentalUIActivator;
import com.opcoach.rcpa.rental.ui.RentalUIConstants;
import com.opcoach.training.rental.RentalAgency;
import com.opcoach.training.rental.helpers.RentalAgencyGenerator;

public class RentalAgencyView extends ViewPart implements IPropertyChangeListener, ISelectionListener, RentalUIConstants
{
	public static final String VIEW_ID = "com.opcoach.rental.ui.rentalagencyview";

	private TreeViewer agencyViewer;

	private AgencyLabelProvider labelProvider;

	public RentalAgencyView()
	{
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent)
	{
		parent.setLayout(new GridLayout(1, false));

		final Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayout(new GridLayout(2, false));
		ImageRegistry imgReg = RentalUIActivator.getDefault().getImageRegistry();
		Button expandAll = new Button(comp, SWT.FLAT);
		expandAll.setImage(imgReg.get(IMG_EXPAND_ALL));
		expandAll.setToolTipText("Expand agency tree");
		expandAll.addSelectionListener(new SelectionListener()
			{
				@Override
				public void widgetSelected(SelectionEvent e)
				{
					agencyViewer.expandAll();
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e)
				{
				}
			});
		Button collapseAll = new Button(comp, SWT.FLAT);
		collapseAll.setImage(imgReg.get(IMG_COLLAPSE_ALL));
		collapseAll.setToolTipText("Collapse agency tree");
		collapseAll.addSelectionListener(new SelectionListener()
			{

				@Override
				public void widgetSelected(SelectionEvent e)
				{
					agencyViewer.collapseAll();
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e)
				{
				}
			});

		agencyViewer = new TreeViewer(parent);
		agencyViewer.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,true));
		agencyViewer.setContentProvider(new AgencyContentProvider());
		labelProvider = new AgencyLabelProvider();
		agencyViewer.setLabelProvider(labelProvider);

		Collection<RentalAgency> agencies = new ArrayList<RentalAgency>();
		agencies.add(RentalCoreActivator.getAgency());

		RentalAgency lyon = RentalAgencyGenerator.createSampleAgency();
		lyon.setName("Lyon");
		agencies.add(lyon);

		agencyViewer.setInput(agencies);

		// Association de la vue sur un contexte d'aide
		PlatformUI.getWorkbench().getHelpSystem()
				.setHelp(agencyViewer.getControl(), "com.opcoach.rcpa.rental.ui.rentalContext");

		// Autorise le popup sur le treeviewer
		MenuManager menuManager = new MenuManager();
		Menu menu = menuManager.createContextMenu(agencyViewer.getControl());
		agencyViewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuManager, agencyViewer);


		getSite().setSelectionProvider(agencyViewer);

	}

	@Override
	public void init(IViewSite site) throws PartInitException
	{
		super.init(site);
		// On s'enregistre en tant que pref listener sur le preference store...
		RentalUIActivator.getDefault().getPreferenceStore().addPropertyChangeListener(this);

		// This treeview is now selection listener to be synchronized with the
		// dashboard.
		getSite().getPage().addSelectionListener(this);

	}

	@Override
	public void dispose()
	{
		RentalUIActivator.getDefault().getPreferenceStore().removePropertyChangeListener(this);

		// This treeview must remove the selection listener
		getSite().getPage().removeSelectionListener(this);

		super.dispose();
	}

	@Override
	public void propertyChange(PropertyChangeEvent event)
	{
		labelProvider.initPalette();
		agencyViewer.refresh();
	}

	@Override
	public void setFocus()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection)
	{
		// Must check if this selection is coming from this part or from another
		// one.
		if (part != this)
			agencyViewer.setSelection(selection, true);

	}

}
