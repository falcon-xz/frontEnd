package com.xz.lucene.demo;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by xz on 2018/12/12.
 */
public class Demo1 {

    private Directory indexDirectory = null;

    @Before
    public void before() {
        try {
            //指定索引路径
            Path path = Paths.get("F:\\lucene\\index");
            //指定索引库的存放位置Directory对象
            indexDirectory = FSDirectory.open(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建索引库
     */
    @Test
    public void createIndex() {
        try {
            //指定一个标准分析器，对文档内容进行分析
            Analyzer analyzer = new StandardAnalyzer();
            //创建indexwriterCofig对象
            IndexWriterConfig config = new IndexWriterConfig(analyzer);

            //创建一个indexwriter对象
            IndexWriter indexWriter = new IndexWriter(indexDirectory, config);
            //原始文档的路径
            File file = new File("F:\\lucene\\content");
            File[] fileList = file.listFiles();
            for (File file2 : fileList) {
                //创建document对象
                Document document = new Document();

                //创建field对象，将field添加到document对象中

                //文件名称
                String fileName = file2.getName();
                //创建文件名域   域的名称  域的内容  是否存储
                Field fileNameField = new TextField("fileName", fileName, Field.Store.YES);
                //文件的大小
                long fileSize = FileUtils.sizeOf(file2);
                //文件大小域
                Field fileSizeField = new StoredField("fileSize", fileSize+"");

                //文件路径
                String filePath = file2.getPath();
                //文件路径域（不分析、不索引、只存储）
                Field filePathField = new StoredField("filePath", filePath);

                //文件内容
                String fileContent = FileUtils.readFileToString(file2, "utf-8");
                //文件内容域
                Field fileContentField = new TextField("fileContent", fileContent, Field.Store.YES);

                document.add(fileNameField);
                document.add(fileSizeField);
                document.add(filePathField);
                document.add(fileContentField);
                //使用indexwriter对象将document对象写入索引库，此过程进行索引创建。并将索引和document对象写入索引库。
                indexWriter.addDocument(document);
            }

            indexWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * query 方式查找全部文档
     */
    @Test
    public void MatchAllDocsQuery() {
        try {
            //创建IndexReader对象，需要指定Directory对象
            IndexReader indexReader = DirectoryReader.open(indexDirectory);
            //创建Indexsearcher对象，需要指定IndexReader对象
            IndexSearcher indexSearcher = new IndexSearcher(indexReader);

            //创建查询条件
            //使用MatchAllDocsQuery查询索引目录中的所有文档
            Query query = new MatchAllDocsQuery();
            //执行查询
            //第一个参数是查询对象，第二个参数是查询结果返回的最大值
            TopDocs topDocs = indexSearcher.search(query, 10);

            //查询结果的总条数
            System.out.println("查询结果的总条数：" + topDocs.totalHits);
            //遍历查询结果
            //topDocs.scoreDocs存储了document对象的id
            //ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
                this.toString(scoreDoc,indexSearcher);
            }
            //关闭indexreader对象
            indexReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * TermQuery 不使用分词器的精准查找term
     */
    @Test
    public void TermQuery() {

        try {
            //创建IndexReader对象，需要指定Directory对象
            IndexReader indexReader = DirectoryReader.open(indexDirectory);
            //创建Indexsearcher对象，需要指定IndexReader对象
            IndexSearcher indexSearcher = new IndexSearcher(indexReader);
            //创建一个TermQuery（精准查询）对象，指定查询的域与查询的关键词
            //创建查询
            Query query = new TermQuery(new Term("fileName", "试"));
            //执行查询
            //第一个参数是查询对象，第二个参数是查询结果返回的最大值
            TopDocs topDocs = indexSearcher.search(query, 10);
            //查询结果的总条数
            System.out.println("查询结果的总条数：" + topDocs.totalHits);
            //遍历查询结果
            //topDocs.scoreDocs存储了document对象的id
            //ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
                this.toString(scoreDoc,indexSearcher);
            }
            //关闭indexreader对象
            indexReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * SortedNumericDocValuesField

    @Test
    public void testNumericRangeQuery() {
        try {
            //创建IndexReader对象，需要指定Directory对象
            IndexReader indexReader = DirectoryReader.open(indexDirectory);
            //创建Indexsearcher对象，需要指定IndexReader对象
            IndexSearcher indexSearcher = new IndexSearcher(indexReader);

            //创建查询
            //参数：
            //1.域名
            //2.最小值
            //3.最大值
            //4.是否包含最小值
            //5.是否包含最大值

            Query query = NumericRangeQuery.newLongRange("fileSize",200l,297l,true,true) ;
            //执行查询

            //第一个参数是查询对象，第二个参数是查询结果返回的最大值
            TopDocs topDocs = indexSearcher.search(query, 10);

            //查询结果的总条数
            System.out.println("查询结果的总条数："+ topDocs.totalHits);
            //遍历查询结果
            //topDocs.scoreDocs存储了document对象的id
            //ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
                this.toString(scoreDoc,indexSearcher);
            }
            //关闭indexreader对象
            indexReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    @Test
    public void testBooleanQuery() {
        try {
            //创建IndexReader对象，需要指定Directory对象
            IndexReader indexReader = DirectoryReader.open(indexDirectory);
            //创建Indexsearcher对象，需要指定IndexReader对象
            IndexSearcher indexSearcher = new IndexSearcher(indexReader);

            //创建查询
            BooleanQuery.Builder builder = new BooleanQuery.Builder() ;
            Query query1 = new TermQuery(new Term("fileName", "测"));
            Query query2 = new TermQuery(new Term("fileName", "试"));
            Query query3 = new TermQuery(new Term("fileContent", "管理员"));
            builder.add(query1, BooleanClause.Occur.MUST) ;
            builder.add(query2, BooleanClause.Occur.MUST) ;
            builder.add(query3, BooleanClause.Occur.MUST_NOT) ;
            BooleanQuery booleanQuery = builder.build() ;
            //执行查询

            //第一个参数是查询对象，第二个参数是查询结果返回的最大值
            TopDocs topDocs = indexSearcher.search(booleanQuery, 10);

            //查询结果的总条数
            System.out.println("查询结果的总条数："+ topDocs.totalHits);
            //遍历查询结果
            //topDocs.scoreDocs存储了document对象的id
            //ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
                this.toString(scoreDoc,indexSearcher);
            }
            //关闭indexreader对象
            indexReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void toString(ScoreDoc scoreDoc,IndexSearcher indexSearcher) throws IOException{
        //scoreDoc.doc属性就是document对象的id
        int doc = scoreDoc.doc;
        System.out.println("document对象的id:" + doc);
        //根据document的id找到document对象
        Document document = indexSearcher.doc(scoreDoc.doc);
        //文件名称
        System.out.println(document.get("fileName"));
        //文件内容
        //System.out.println(document.get("fileContent"));
        //文件大小
        System.out.println(document.get("fileSize"));
        //文件路径
        System.out.println(document.get("filePath"));
        System.out.println("----------------------------------");
    }
}
