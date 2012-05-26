package eu.wuttke.tinyscrum.ui.misc;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.Map;

import com.vaadin.terminal.DownloadStream;
import com.vaadin.terminal.ParameterHandler;
import com.vaadin.terminal.URIHandler;

import eu.wuttke.tinyscrum.domain.FileUpload;

public class FileDownloadHandler implements URIHandler, ParameterHandler {

	private Long binaryId;
	
	@Override
	public void handleParameters(Map<String, String[]> parameters) {
		if (parameters.containsKey("binaryId")) {
			try {
				binaryId = Long.parseLong(parameters.get("binaryId")[0]);
			} catch (NumberFormatException e) {
				binaryId = null;
			}
		}
	}

	@Override
	public DownloadStream handleURI(URL context, String relativeUri) {
		if (relativeUri.endsWith("getFile") && binaryId != null) {
			FileUpload fu = FileUpload.findFileUpload(binaryId);
			if (fu != null)
				return new DownloadStream(new ByteArrayInputStream(fu.getBinaryData()), fu.getMimeType(), fu.getFileName());
		} 

		return null;
	}

	private static final long serialVersionUID = 1L;

}
