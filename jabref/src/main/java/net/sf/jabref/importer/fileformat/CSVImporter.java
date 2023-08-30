package net.sf.jabref.importer.fileformat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import net.sf.jabref.importer.ImportFormatReader;
import net.sf.jabref.importer.OutputPrinter;
import net.sf.jabref.model.entry.BibEntry;
import net.sf.jabref.model.entry.BibtexEntryTypes;

public class CSVImporter extends ImportFormat {

    @Override
    public String getFormatName() {
        return "CSV";
    }

    @Override
    public boolean isRecognizedFormat(InputStream stream) throws IOException {
        return true;
    }

    @Override
    public String getExtensions() {
        return "csv";
    }

@Override
public List<BibEntry> importEntries(InputStream stream, OutputPrinter printer) throws IOException {
    List<BibEntry> bibEntryItems = new ArrayList<>();
  BufferedReader entryFile = new BufferedReader(ImportFormatReader.getReaderDefaultEncoding(stream));
  String line = entryFile.readLine();
  String[] fields;

  while (line != null) {
    if (!line.trim().isEmpty()) {
      fields = line.split(";");
      BibEntry entry = new BibEntry();
      if (fields[0].equals("book")) {
        entry.setType (BibtexEntryTypes.BOOK);
        entry.setField("title", fields[1]);
        entry.setField("publisher", fields[2]);
        entry.setField("year", fields[3]);
        entry.setField("author", fields[4]);
        entry.setField("editor", fields[5]);
        entry.setField("bibtexkey", fields[6]);
    } else if (fields[0].equals("article")) {
        entry.setType (BibtexEntryTypes.ARTICLE);
        entry.setField("author", fields[1]);
        entry.setField("title", fields[2]);
        entry.setField("journal", fields[3]);
        entry.setField("year", fields[4]);
        entry.setField("bibtexkey", fields[5]);
    }
    bibEntryItems.add(entry);
    line = entryFile.readLine();
    }
  }
  return bibEntryItems;
}
}