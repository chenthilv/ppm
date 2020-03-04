import React, { Component } from "react";
import CreateProjectButton from "./Project/CreateProjectButton";
class Dashboard extends Component {
  render() {
    return (
      <div class="px-4 py-3">
        <div>
          <h4>Welcome to Dashboard</h4>
        </div>
        <div>
          {" "}
          <CreateProjectButton />
        </div>
      </div>
    );
  }
}

export default Dashboard;
