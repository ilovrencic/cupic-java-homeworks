package hr.fer.zemris.java.hw11.jnotepadapp.models;

/**
 * Interface of the single document listener that listening for document
 * modification or file path update.
 * 
 * @author ilovrencic
 *
 */
public interface SingleDocumentListener {

	/**
	 * Method that is called when modify status has changed.
	 * 
	 * @param model - instance of the {@link SingleDocumentModel}
	 */
	void documentModifyStatusUpdated(SingleDocumentModel model);

	/**
	 * Method that is called when document file path has updated.
	 * 
	 * @param model - instance of the {@link SingleDocumentModel}
	 */
	void documentFilePathUpdated(SingleDocumentModel model);

}
