package gr.media24.mSites.data.enums;

import gr.media24.mSites.data.entities.Article;

import java.util.List;

import com.google.common.base.Predicate;

/**
 * @author nk, tk
 */
public class Predicates {

	public static Predicate<Article> forArticleType(String types) {
		return new ForArticleType(types);
	}

	private static class ForArticleType implements Predicate<Article> {
		final String types;

		private ForArticleType(String types) {
			this.types = types;
		}

		public boolean apply(Article article) {
			if(types == null || types.isEmpty()) return true;
			List<ArticleType> articleTypes = ArticleType.getListOf(types);
			return articleTypes.contains(article.getArticleType());
		}
	}
}