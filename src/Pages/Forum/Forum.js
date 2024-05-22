import { useEffect, useState } from "react";
import { getRequestFetchingLogic, fetchResponseRequest } from "../../Api";
import Category from "../Forum/Category";

const Forum = () => {
  const [categories, setCategories] = useState([]);
  const [isAdmin, setIsAdmin] = useState(false);

  useEffect(() => {
    fetchCategories();
  }, []);

  const fetchCategories = async () => {
    const response = await getRequestFetchingLogic("/api/categories");
    if (response && response.user) {
      checkAdminStatus(response);
    } else {
      console.error("User data is missing from the response");
    }
    setCategories(response);
  };

  const checkAdminStatus = (data) => {
    const isAdminRole = data.user.roles
      ? data.user.roles.includes("ROLE_ADMIN")
      : false;
    setIsAdmin(isAdminRole);
  };

  const handleDeleteCategory = async (postId) => {
    const response = await fetchResponseRequest(
      `/api/categories/delete/${postId}`
    );
    if (response) {
      fetchCategories();
    }
  };

  return (
    <div>
      <h1>Forum Categories</h1>
      {categories.map((category) => (
        <Category
          key={category.id}
          category={category}
          isAdmin={isAdmin}
          onDelete={handleDeleteCategory}
        />
      ))}
    </div>
  );
};

export default Forum;
