import { fetchRequest } from "../../Api";

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
      ...(isEdit && { roles: [{ roles: formData.get("role") }] }),
    };

    // if (isEdit) {
    //   userDetails.phone = formData.get("phone");
    // }

    let endpoint = "/api/auth/register";
    if (isEdit) {
      endpoint = `/api/users/${passedUserDetails.id}`;
    } else if (isAdd) {
      endpoint = "/api/users";
    }

    await fetchRequest(endpoint, userDetails);

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
      {isEdit && (
        <div>
          <label htmlFor="role">User Role:</label>
          <select
            id="role"
            name="role"
            defaultValue={
              passedUserDetails ? passedUserDetails.roles[0].id : ""
            }>
            <option value="">Select a role</option>
            <option value="1">Admin</option>
            <option value="2">User</option>
            <option value="3">Coach</option>
          </select>
        </div>
      )}
      <button type="submit" className="signup-button">
        Upiši se
      </button>
    </form>
  );
};

export default Signup;
