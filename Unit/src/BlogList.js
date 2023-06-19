import { Link, useHistory } from "react-router-dom";

const BlogList = ({ blogs, title }) => {
  //   const history = useHistory();
  //   const handleClick = (id) => {
  //     fetch("http://localhost:8000/blogs/" + id, {
  //       method: "DELETE",
  //     }).then(() => {
  //       history.push("/");
  //     });
  //   };
  return (
    <>
      <h2>{title}</h2>
      <div className="blog-list">
        {blogs.map((blog) => (
          <div className="blog-preview" key={blog.id}>
            <Link to={`/blogs/${blog.id}`}>
              <h2>{blog.title}</h2>
              <p>Written by {blog.author}</p>
              {/* <button onClick={handleClick(blog.id)}>delete</button> */}
            </Link>
          </div>
        ))}
      </div>
    </>
  );
};

export default BlogList;
