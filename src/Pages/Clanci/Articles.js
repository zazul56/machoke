import { useEffect, useState } from "react";
import { getRequestFetchingLogic } from "../../Api";
const Articles = () => {
  const [articles, setArticles] = useState([]);

  const fetchArticles = async () => {
    const response = await getRequestFetchingLogic("/api/articles");
    setArticles(response);
  };

  useEffect(() => {
    fetchArticles();
  }, []);

  return (
    <div>
      <h1>Articles</h1>
      <div className="articles-list">
        {articles.map((article) => (
          <div key={article.id} className="article-item">
            <h2>{article.key}</h2>
            <p>{article.summary}</p>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Articles;
