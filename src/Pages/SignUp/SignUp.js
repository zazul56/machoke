import { fetchRequest } from "../../getRequestFetchingLogic";

const Signup = ({ passedUserDetails, isEdit, isAdd }) => {
  const handleSubmit = async (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);

    const userDetails = {
      firstname: formData.get("firstname"),
      lastname: formData.get("lastname"),
      username: formData.get("username"),
      email: formData.get("email"),
      password: formData.get("password"),
    };
    let endpoint;
    if (isEdit) {
      endpoint = `/api/users/${passedUserDetails.id}`;
      await fetchRequest(endpoint, userDetails);
      //poziv za update
    } else if (isAdd) {
      await fetchRequest("/api/users", userDetails);
    } else {
      await fetchRequest("/api/auth/register", userDetails);
    }

    // console.log("data", data); //stp radi, s responsom? neka provera? ovo vise izgleda kao login xd
  };

  return (
    <form onSubmit={handleSubmit} className="form">
      <label htmlFor="fname">First name:</label>
      <input
        type="text"
        id="fname"
        name="firstname"
        defaultValue={passedUserDetails ? passedUserDetails.firstname : ""}
      />

      <label htmlFor="lname">Last name:</label>
      <input
        type="text"
        id="lname"
        name="lastname"
        defaultValue={passedUserDetails ? passedUserDetails.lastname : ""}
      />

      <label htmlFor="username">Username:</label>
      <input
        type="text"
        id="username"
        name="username"
        defaultValue={passedUserDetails ? passedUserDetails.username : ""}
      />

      <label htmlFor="email">Email:</label>
      <input
        type="email"
        id="email"
        name="email"
        defaultValue={passedUserDetails ? passedUserDetails.email : ""}
      />

      <label htmlFor="password">Password:</label>
      <input
        type="password"
        id="password"
        name="password"
        defaultValue={passedUserDetails ? passedUserDetails.password : ""}
      />

      <button type="submit" className="signup-button">
        Upiši se
      </button>
    </form>
  );
};

export default Signup;
