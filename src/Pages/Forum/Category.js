import { useNavigate } from "react-router-dom";

const Category = ({ category, isAdmin, onDelete }) => {
  const navigate = useNavigate();

  const handleQuestionClick = (categoryId) => {
    navigate(`/forum/questions`, { state: { categoryId } });
  };

  return (
    <div>
      <h2
        className="category-card"
        onClick={() => handleQuestionClick(category.id)}>
        {category.name}
      </h2>
      {isAdmin && (
        <button
          onClick={(e) => {
            e.stopPropagation();
            onDelete(category.id);
          }}>
          Delete
        </button>
      )}
    </div>
  );
};

export default Category;
