import React, { useEffect, useState } from "react";
import { fetchResponseRequest, getRequestFetchingLogic } from "../../Api";
import "./users.css";
import Signup from "../SignUp/SignUp";
const Users = () => {
  const [selectedUser, setSelectedUser] = useState(null);
  const [data, setData] = useState(null);
  const [showModal, setShowModal] = useState(false);

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      const fetchedData = await getRequestFetchingLogic("/api/users");

      const filteredData = fetchedData.filter((item) => !item.deleted);
      setData(filteredData);
    } catch (error) {
      console.error("Error fetching users:", error);
    }
  };

  const onEdit = (userId) => {
    const userToEdit = data.find((user) => user.id == userId);
    //kako cu za edit isti endpoint lol
    setSelectedUser(userToEdit);
    openModal();
  };

  const onDelete = async (userId) => {
    await fetchResponseRequest(`/api/users/delete/${userId}`);
    fetchData();
  };

  const openModal = () => {
    setShowModal(true);
  };

  const closeModal = () => {
    setSelectedUser(null);
    setShowModal(false);
    fetchData();
  };

  return (
    <div className="container">
      {data ? (
        <table className="table">
          <thead>
            <tr>
              <th>ID</th>
              <th>firstname</th>
              <th>lastname</th>
              <th>username</th>
              <th>
                <button className="button add-user" onClick={openModal}>
                  Add User
                </button>
              </th>
            </tr>
          </thead>
          <tbody>
            {data.map((user) => (
              <tr key={user.id}>
                <td>{user.id}</td>
                <td>{user.firstname}</td>
                <td>{user.lastname}</td>
                <td>{user.username}</td>
                <td>
                  <button
                    className="button edit"
                    onClick={() => onEdit(user.id)}>
                    Edit
                  </button>
                  <button
                    className="button delete"
                    onClick={() => onDelete(user.id)}>
                    Delete
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      ) : (
        <p>Loading users...</p>
      )}

      {showModal && (
        <div className="modal">
          <div className="modal-content">
            <span className="close-button" onClick={closeModal}>
              &times;
            </span>
            <Signup
              passedUserDetails={selectedUser}
              isEdit={selectedUser}
              isAdd={!selectedUser}
            />
          </div>
        </div>
      )}
    </div>
  );
};

export default Users;
