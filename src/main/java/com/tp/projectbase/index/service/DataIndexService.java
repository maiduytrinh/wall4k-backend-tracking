package com.tp.projectbase.index.service;

import com.google.common.base.Stopwatch;
import com.tp.projectbase.Utils;
import com.tp.projectbase.common.config.AppConfiguration;
import com.tp.projectbase.entity.Data;
import com.tp.projectbase.index.AbstractIndex;
import com.tp.projectbase.index.DocumentConstant;
import com.tp.projectbase.index.LuceneIndex;
import com.tp.projectbase.rdbms.PageImpl;
import com.tp.projectbase.rdbms.api.Pageable;
import com.tp.projectbase.storage.impl.DataStorageImpl;

import io.vertx.core.json.JsonObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.SortedNumericDocValuesField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DataIndexService extends AbstractIndex<Data, Data> {

	protected static final Logger LOG = LoggerFactory.getLogger(DataIndexService.class);

	private final static String INDEX_NAME = "/data";

	private LuceneIndex indexer = null;
	private Directory directory = null;
	private Boolean isIndexing = false;

	public DataIndexService() {
		super(INDEX_NAME);
		try {
			directory = FSDirectory.open(Paths.get(parentDirectory + INDEX_NAME));
			indexer = new LuceneIndex(directory, new StandardAnalyzer(stopSet));
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
	}

	@Override
	public void init() {
		try {
			boolean isFuzzyActivate = AppConfiguration.getBoolean(AppConfiguration.SERVICE_FUZZY_SEARCH_ACTIVATE, "true");
			if (!isFuzzyActivate) {
				return;
			}

			if (DirectoryReader.indexExists(directory) || isIndexing) {
				LOG.info("DataIndex::directory indexing is existing.");
				return;
			}

			isIndexing = true;

			initAllIndexing();

		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		} finally {
			if (isIndexing) {
				isIndexing = false;
			}
		}
	}

	@Override
	public JsonObject initAllIndexing() {
		final Stopwatch timer = Stopwatch.createStarted();
		long inserted = 0;
		Pageable<Data> pageable = new PageImpl<>(0, 1000);
		Pageable<Data> nextPageList;
		int records = 0;
		do {
			nextPageList = pageable.nextPageable();
			pageable = DataStorageImpl.getPageable(nextPageList);
			int sizeList = pageable.getList().size();
			records += sizeList;
			inserted += sizeList;
			this.createPageIndex(pageable.getList());
		} while (pageable.hasNext());
		LOG.info("Collection::Index {} records", records);
		long minutes = timer.elapsed(TimeUnit.MINUTES);
		LOG.info("CollectionIndex::Indexing took(minute: {})", timer.stop());
		JsonObject result = new JsonObject();
		result.put("inserted", inserted);
		result.put("indexing took minute(s)", minutes);
		return result;
	}


	@Override
	public Long updateIndex(Data model) {
		if (model.getId() == null) return -1L;
		Term term = new Term("id", model.getId().toString());
		Document document = this.create(model);
		if (document == null) return -1L;
		return indexer.updateDocument(term, document);
	}

	@Override
	public LuceneIndex getIndexer() {
		return indexer;
	}

	@Override
	public Directory getDirectory() {
		return directory;
	}

	@Override
	public Boolean getIsIndexing() {
		return isIndexing;
	}

	@Override
	public void setIsIndexing(Boolean isIndexing) {
		this.isIndexing = isIndexing;
	}

	@Override
	public String getClassName() {
		return DataIndexService.class.getSimpleName();
	}

	@Override
	public List<Data> convertDocuments(List<Document> documents) {
		if (CollectionUtils.isEmpty(documents)) return new ArrayList<>();
		List<Data> result = new ArrayList<>();
		for (Document document : documents) {
			try {
				Data data = new Data();
				data.setId(Utils.parseInt(document.get("id")));
				data.setCreatedDate(Utils.parseLong(document.get("createdDate")));
				result.add(data);
			} catch (Exception e) {
				LOG.error("Error convertToHashtag::document: {}", document);
				LOG.error(e.getMessage());
			}
		}
		return result;
	}

	@Override
	public Document create(Data model) {
		final Document document = new Document();
		document.add(new StringField(DocumentConstant.ID, model.getId().toString(), Field.Store.YES));
		document.add(new StringField(DocumentConstant.CREATED_DATE, model.getCreatedDate() != null ? model.getCreatedDate().toString() : "0", Field.Store.YES));
		document.add(new SortedNumericDocValuesField(DocumentConstant.CREATED_DATE, model.getCreatedDate()));
		return document;
	}

}
