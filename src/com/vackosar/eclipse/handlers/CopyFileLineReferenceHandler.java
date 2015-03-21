package com.vackosar.eclipse.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

public class CopyFileLineReferenceHandler extends AbstractHandler {
	public CopyFileLineReferenceHandler() {
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {
		IEditorPart activeEditor = HandlerUtil.getActiveEditor(event);
		String file = getFileName(activeEditor);
		int startLine = getSelectionStartLine(activeEditor);
		setClipboard(file, startLine);
		return null;
	}

	private int getSelectionStartLine(IEditorPart activeEditor) {
		ISelection selection = activeEditor.getEditorSite().getSelectionProvider().getSelection();
		if (selection instanceof TextSelection) {
			TextSelection ts = (TextSelection) selection;
			return ts.getStartLine();
		}
		return 0;
	}

	private String getFileName(IEditorPart activeEditor) {
		return activeEditor.getEditorInput().getName();
	}

	private void setClipboard(String file, int startLine) {
		final Clipboard clipboard = new Clipboard(Display.getCurrent());
		clipboard.setContents(getContentObject(file, startLine), getTransfer());
		clipboard.dispose();
	}

	private Transfer[] getTransfer() {
		return new Transfer[]{ TextTransfer.getInstance() };
	}

	private Object[] getContentObject(String file, int startLine) {
		return new Object[]{ getContent(file, startLine)};
	}

	private String getContent(String file, int startLine) {
		return "(" + file + ":" + startLine + ")";
	}
}
