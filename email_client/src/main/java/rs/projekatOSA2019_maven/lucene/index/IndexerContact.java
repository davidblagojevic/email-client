package rs.projekatOSA2019_maven.lucene.index;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import rs.projekatOSA2019_maven.lucene.IndexableDocumentContact;
import rs.projekatOSA2019_maven.lucene.SerbianAnalyzer;

public class IndexerContact {
	public static Document createDocument(IndexableDocumentContact documentContact) {
		Document document = new Document();
		
		IndexableField indexFirstName = new StringField("firstname", documentContact.getFirstname(), Store.YES);
		IndexableField indexLastName = new StringField("lastName", documentContact.getLastname(), Store.YES);
		IndexableField indexEmail = new StringField("email", documentContact.getEmail(), Store.YES);
		IndexableField indexNote = new StringField("note", documentContact.getNote(), Store.YES);
		document.add(indexFirstName);
		document.add(indexLastName);
		document.add(indexEmail);
		document.add(indexNote);
		return document;
	}
	
	public static void indexDocument (Document document) throws IOException {
		IndexWriter iWriter = null;
		try {
			ResourceBundle rb = ResourceBundle.getBundle("application", Locale.getDefault());
			Directory indexDirectory = FSDirectory.open(new File(rb.getString("dataDir")));
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
