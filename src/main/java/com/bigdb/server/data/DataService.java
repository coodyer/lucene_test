package com.bigdb.server.data;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.SortField.Type;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

import com.bigdb.server.cache.LocalCache;
import com.bigdb.server.util.EncryptUtil;
import com.bigdb.server.util.PropertyUtil;

public class DataService {
	
	private static final Logger logger=Logger.getLogger(DataService.class);
	

	private static final String BIG_DATA_SEARCH = "BIG_DATA_SEARCH";
	
	private static final String LUCENE_INDEX_PATH = "LUCENE_INDEX_PATH";

	static Analyzer analyzer = new StandardAnalyzer();// 词库分词

	public static List<String> search(String keyWorld) {
		String key = BIG_DATA_SEARCH + "_" + EncryptUtil.md5Code(keyWorld);
		List<String> result = LocalCache.getCache(key);
		if (result != null) {
			return result;
		}
		result = query(keyWorld);
		logger.info("搜索关键字:"+keyWorld+"="+result);
		if (result == null) {
			result = new ArrayList<String>();
		}
		LocalCache.setCache(key, result, 5);
		return result;
	}

	private static String getIndexPath(){
		String path=LocalCache.getCache(LUCENE_INDEX_PATH);
		if(path==null){
			path=PropertyUtil.getConf("lucene_dir");
			LocalCache.setCache(LUCENE_INDEX_PATH, path, 60);
		}
		return path;
	}
	/**
	 * 查询搜索
	 * 
	 * @throws IOException
	 * @throws ParseException
	 */
	private static List<String> query(String keyWorld) {
		IndexReader reader = null;
		try {
			reader = DirectoryReader.open(FSDirectory.open(Paths.get(getIndexPath())));
			IndexSearcher searcher = new IndexSearcher(reader);
			// 排序
			Sort sort = new Sort();
			sort.setSort(new SortField("context", Type.SCORE)); // 默认为升序
			// 2、搜索解析器
			QueryParser parser = new QueryParser("content", analyzer);
			Query query = parser.parse(keyWorld);
			TopDocs topdocs = searcher.search(query, 100, sort);
			if (topdocs.totalHits < 1) {
				return null;
			}
			ScoreDoc scores[] = topdocs.scoreDocs;// 得到所有结果集
			List<String> results = new ArrayList<String>();
			for (int i = 0; i < scores.length; i++) {
				int num = scores[i].doc;// 得到文档id
				Document document = searcher.doc(num);// 拿到指定的文档
				results.add(document.get("content"));
			}
			return results;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
