package gr.soule.form.core.utils;

import gr.media24.mSites.core.tags.ArticleUrlTag;
import gr.media24.mSites.data.entities.Article;
import gr.media24.mSites.data.entities.Newspaper;

import java.util.List;

/**
 * @author nk, tk
 */
public class NewspaperView {

	private Article newspaper;
	private List<Article> priorityList;
	private int index;
	private int size;

	public NewspaperView(Article newspaper, List<Article> priorityList) {
		this.newspaper = newspaper;
		this.priorityList = priorityList;
		this.size = priorityList.size();
		this.index = priorityList.indexOf(newspaper);
	}

	public boolean getHasPrevious() {
		return index>0 && size>0;
	}

	public boolean getHasNext() {
		return index < size-1;
	}

	public String getPrevious() {
		return ArticleUrlTag.getArticleUrl(priorityList.get(index-1));
	}

	public String getNext() {
		return ArticleUrlTag.getArticleUrl(priorityList.get(index+1));
	}

	public String getPreviousLoop() {
		int previousIndex = index-1;
		if(!this.getHasPrevious()) previousIndex = size-1;
		return ArticleUrlTag.getArticleUrl(priorityList.get(previousIndex));
	}

	public String getNextLoop() {
		int nextIndex = index+1;
		if(!this.getHasNext()) nextIndex = 0;
		return ArticleUrlTag.getArticleUrl(priorityList.get(nextIndex));
	}

	public String getCategoryName() {
		return newspaper.getCategories().get(0).getName(); //Categories Are LAZY Loaded, Make Sure That You Have Properly Initialized The Article
	}

	public String getUrl() {
		return ((Newspaper) newspaper).getAlternate();
	}

	public Article getArticle() {
		return newspaper;
	}

	public int getIndex() {
		return index;
	}

	public int getSize() {
		return size;
	}
}