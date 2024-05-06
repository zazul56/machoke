import { useEffect, useState } from "react";
import { getRequestFetchingLogic } from "../../Api";
import Category from "../Forum/Category";

const Forum = () => {
  const [categories, setCategories] = useState([]);

  useEffect(() => {
    const fetchCategories = async () => {
      const response = await getRequestFetchingLogic("/api/categories");
      setCategories(response);
    };
    fetchCategories();
  }, []);

  return (
    <div>
      <h1>Forum Categories</h1>
      {categories.map((category) => (
        <Category key={category.id} category={category}>
          baba {category.name}
        </Category>
      ))}
    </div>
  );
};

export default Forum;
