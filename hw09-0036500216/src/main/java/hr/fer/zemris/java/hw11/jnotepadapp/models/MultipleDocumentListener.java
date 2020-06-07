package hr.fer.zemris.java.hw11.jnotepadapp.models;

/**
 * Interface that serves as a listener {@link MultipleDocumentModel}.
 * 
 * @author ilovrencic
 *
 */
public interface MultipleDocumentListener {

	/**
	 * Method thats called if the current opened document has changed.
	 * 
	 * @param previousModel - previously opened document
	 * @param currentModel  - currently opened document
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);

	/**
	 * Method thats called if the new {@link SingleDocumentModel} is added.
	 * 
	 * @param model - instance of the {@link SingleDocumentModel}
	 */
	void documentAdded(SingleDocumentModel model);

	/**
	 * Method thats called if the {@link SingleDocumentModel} is removed.
	 * 
	 * @param model - instance of the {@link SingleDocumentModel}
	 */
	void documentRemoved(SingleDocumentModel model);

}
