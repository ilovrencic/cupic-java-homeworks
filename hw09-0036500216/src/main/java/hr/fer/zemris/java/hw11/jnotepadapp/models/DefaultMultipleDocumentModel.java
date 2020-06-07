package hr.fer.zemris.java.hw11.jnotepadapp.models;

import java.awt.BorderLayout;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Class implementation of the {@link MultipleDocumentModel}. Here we are
 * managing the logic of having more {@link SingleDocumentModel}s.
 * 
 * @author ilovrencic
 *
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

	/**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = -6789434015559077293L;

	/**
	 * List of the active {@link SingleDocumentModel}.
	 */
	private List<SingleDocumentModel> documents;

	/**
	 * Represents a instance of the current {@link SingleDocumentModel}
	 */
	private SingleDocumentModel currentDocument;

	/**
	 * List of the {@link MultipleDocumentListener}
	 */
	private List<MultipleDocumentListener> listeners;

	/**
	 * Current {@link SingleDocumentListener}
	 */
	private SingleDocumentListener currentListener;

	/**
	 * Default constructor
	 */
	public DefaultMultipleDocumentModel() {
		documents = new ArrayList<SingleDocumentModel>();
		listeners = new ArrayList<MultipleDocumentListener>();

		// listener that checks whether the tab has changed
		addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				changeCurrentDocument(getSelectedIndex() == -1 ? null : documents.get(getSelectedIndex()));
			}
		});

		// current listener that checks what has changed to the active document
		currentListener = new SingleDocumentListener() {

			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				// todo add icons
			}

			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {
				setToolTipTextAt(getSelectedIndex(), model.getFilePath().toAbsolutePath().toString());
				setTitleAt(getSelectedIndex(), model.getFilePath().getFileName().toString());
			}
		};
	}

	/**
	 * Method that changes current active document. We have to remove current
	 * listener from previous document and attach it to the new current.
	 * 
	 * @param document - instance of the document that is going to be new active
	 *                 document
	 */
	private void changeCurrentDocument(SingleDocumentModel document) {
		if (document == null) {
			currentDocument = null;
			return;
		}

		if (currentDocument == null) {
			currentDocument = document;
			listeners.forEach(l -> l.currentDocumentChanged(null, document));
		} else {
			SingleDocumentModel previousDocument = currentDocument;
			previousDocument.removeSingleDocumentListener(currentListener);
			currentDocument = document;
			listeners.forEach(l -> l.currentDocumentChanged(previousDocument, currentDocument));
		}

		currentDocument.addSingleDocumentListener(currentListener);
	}

	/**
	 * Method that checks if there is already opened document with that path.
	 * 
	 * @param path - instance of the {@link Path}
	 * @return - instance of the {@link SingleDocumentModel} if there is one,
	 *         otherwise null
	 */
	private SingleDocumentModel checkIfThisPathIsActive(Path path) {
		for (SingleDocumentModel document : documents) {
			if (document.getFilePath() == null)
				continue;
			if (document.getFilePath().equals(path)) {
				int index = documents.indexOf(document);
				setSelectedIndex(index);
				return document;
			}
		}
		return null;
	}

	/**
	 * Method that adds document to the list of active documents.
	 * 
	 * @param document - instance of the {@link SingleDocumentModel}
	 */
	private void addDocument(SingleDocumentModel document) {
		int size = documents.size();
		documents.add(document);

		JPanel panel = new JPanel(new BorderLayout());
		JScrollPane scrollPane = new JScrollPane(document.getTextComponent());
		panel.add(scrollPane, BorderLayout.CENTER);

		if (document.getFilePath() == null) {
			addTab("(unnamed)", null, panel, "file not saved");
		} else {
			addTab(document.getFilePath().getFileName().toString(), null, panel,
					document.getFilePath().toAbsolutePath().toString());
		}

		setSelectedIndex(size);
		listeners.forEach(l -> l.documentAdded(document));
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		SingleDocumentModel newDocument = new DefaultSingleDocumentModel(null, null);
		addDocument(newDocument);
		return newDocument;
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return currentDocument;
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		Objects.requireNonNull(path);

		SingleDocumentModel document = checkIfThisPathIsActive(path);
		if (document != null)
			return document;

		String loadedText = null;
		if (Files.exists(path)) {
			try {
				byte[] bytes = Files.readAllBytes(path);
				loadedText = new String(bytes, StandardCharsets.UTF_8);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, "Pogreška!", "Ne postoji takva datoteka!",
						JOptionPane.ERROR_MESSAGE);
				return null;
			}
		}

		document = new DefaultSingleDocumentModel(loadedText, path);
		addDocument(document);
		return document;
	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		if (newPath == null) {
			newPath = model.getFilePath();
		} else if (Files.exists(newPath)) {
			JOptionPane.showMessageDialog(this, "Upozorenje!", "Ova datoteka već postoji!",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		byte[] bytes = model.getTextComponent().getText().getBytes(StandardCharsets.UTF_8);

		try {
			Files.write(newPath, bytes);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Pogreška!", "Dogodila se pogreška!", JOptionPane.ERROR_MESSAGE);
			return;
		}

		JOptionPane.showMessageDialog(this, "Uspjeh!", "Uspješno spremljena datoteka!",
				JOptionPane.INFORMATION_MESSAGE);

		model.setModified(false);
		model.setFilePath(newPath);
		currentListener.documentFilePathUpdated(model);
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		documents.remove(model);
		remove(getSelectedIndex());

		if (documents.size() == 0) {
			currentDocument = null;
		}

		listeners.forEach(l -> l.documentRemoved(model));
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		Objects.requireNonNull(l);
		listeners.add(l);
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		Objects.requireNonNull(l);
		listeners.remove(l);
	}

	@Override
	public int getNumberOfDocuments() {
		return documents.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		return documents.get(index);
	}

}
