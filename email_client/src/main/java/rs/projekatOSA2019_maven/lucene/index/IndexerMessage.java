package rs.projekatOSA2019_maven.lucene.index;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import rs.projekatOSA2019_maven.lucene.IndexableDocumentMessage;
import rs.projekatOSA2019_maven.lucene.SerbianAnalyzer;

public class IndexerMessage {
	public static Document createDocument(IndexableDocumentMessage documentMessage) {
		Document document = new Document();
		
		IndexableField indexTo = null;
		IndexableField indexBcc = null;
		// IndexableField indexAccounts = null;
		IndexableField indexFrom = new StringField("messageFrom", documentMessage.getMessageFrom(), Store.YES);
		if(documentMessage.getMessageTo() != null) {
	    	  indexTo = new StringField("messageTo", documentMessage.getMessageTo(), Store.YES);
	    	  document.add(indexTo);
		}	      
		if(documentMessage.getMessageBcc() != null) {
			indexBcc = new StringField("messageBcc", documentMessage.getMessageBcc(), Store.YES);
			document.add(indexBcc);
		}
		IndexableField indexId = new TextField("id", documentMessage.getId(), Store.YES);
		IndexableField indexContent = new TextField("content", documentMessage.getContent(), Store.YES);
		IndexableField indexSubject = new TextField("subject", documentMessage.getSubject(), Store.YES);
		IndexableField indexFolder = new TextField("folder", documentMessage.getFolder(), Store.YES);
		IndexableField indexUnread = new TextField("unread",documentMessage.getUnread().toString(),Store.YES);
		
		document.add(indexFrom);
		// document.add(indexTo);
		// document.add(indexBcc);
		document.add(indexSubject);
		document.add(indexContent); 
		document.add(indexFolder);
		document.add(indexUnread);
	      
		// document.add(accounts);
		document.add(indexId);
		
		return document;
	}
	public static void indexDocument (Document document) throws IOException {
		IndexWriter iWriter = null;
		try {
			ResourceBundle rb = ResourceBundle.getBundle("application", Locale.getDefault());
			Directory indexDirectory = FSDirectory.open(new File(rb.getString("indexDir")));
			Analyzer analyzer = new SerbianAnalyzer();
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_40, analyzer);
			iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
			iWriter = new IndexWriter(indexDirectory, iwc);
			iWriter.addDocument(document);
			iWriter.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (iWriter != null) {
				iWriter.close();
			}
		}
	}
}
