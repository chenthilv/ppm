import React, { Component } from "react";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

class AddProject extends Component {
  constructor(props) {
    super(props);
    this.state = {
      projectName: "",
      projectIdentifier: "",
      description: "",
      startDate: new Date(),
      endDate: ""
    };
  }

  handleChange = e => {
    this.setState({ [e.target.name]: e.target.value });
  };
  render() {
    return (
      <form>
        <div className="container border border-light addproj">
          <div className="form-group">
            <label>Project Name:</label>
            <input
              className="form-control"
              type="text"
              id="projectName"
              name="projectName"
              placeholder="Project Name"
              value={this.state.projectName}
              onChange={this.handleChange}
            />
          </div>
          <div className="form-group">
            <label>Project Identifier:</label>
            <input
              className="form-control"
              type="text"
              id="projectIdentifier"
              name="projectIdentifier"
              placeholder="Project Identifier"
              value={this.state.projectIdentifier}
              onChange={this.handleChange}
            />
          </div>
          <div className="form-group">
            <label>Description:</label>
            <textarea
              className="form-control"
              type="text"
              id="description"
              name="description"
              placeholder="Description"
              value={this.state.description}
              onChange={this.handleChange}
            />
          </div>
          <div className="form-group">
            <label>Start Date:</label>
            <br />
            <DatePicker
              className="form-control datepicker"
              dateFormat="yyyy/MM/dd"
              selected={this.state.startDate}
              onChange={date => this.setState({ startDate: date })}
              placeholderText="Click to select a start date"
              value={this.state.startDate}
            />
          </div>
          <div className="form-group">
            <label>End Date:</label>
            <br />
            <DatePicker
              className="form-control datepicker"
              dateFormat="yyyy/MM/dd"
              selected={this.state.endDate}
              onChange={date => this.setState({ endDate: date })}
              placeholderText="Click to select a end date"
              value={this.state.endDate}
            />
          </div>
          <div>
            <button type="submit" className="btn btn-primary">
              Create
            </button>
          </div>
        </div>
      </form>
    );
  }
}

export default AddProject;
