import React, { Component } from 'react';
import RecordService from '../services/RecordsService';
import AuthService from '../services/AuthService'; // Assuming you have an AuthService to handle authentication

class ListRecordComponent extends Component {
    constructor(props) {
        super(props);

        this.state = {
            records: [],
            searchTerm: ''
        };

        this.addRecords = this.addRecords.bind(this);
        this.editRecords = this.editRecords.bind(this);
        this.deleteRecords = this.deleteRecords.bind(this);
        this.handleSearchChange = this.handleSearchChange.bind(this);
        this.searchRecords = this.searchRecords.bind(this);
        this.logout = this.logout.bind(this);
    }

    deleteRecords(id) {
        RecordService.deleteRecords(id).then(res => {
            this.setState({ records: this.state.records.filter(record => record.id !== id) });
        }).catch(error => {
            console.error('Error deleting record', error);
        });
    }

    editRecords(id) {
        this.props.history.push(`/add-records/${id}`);
    }

    componentDidMount() {
        this.getRecords(''); // Fetch records on mount
    }

    handleSearchChange(event) {
        this.setState({ searchTerm: event.target.value });
    }

    searchRecords() {
        this.getRecords(this.state.searchTerm); // Fetch records with search term
    }

    getRecords(searchTerm) {
        const payload = searchTerm ? { searchValue: searchTerm } : {};

        RecordService.getRecords(payload).then(res => {
            this.setState({ records: res.data || [] });
        }).catch(error => {
            console.error('Error fetching records', error);
            this.setState({ records: [] }); // Reset to empty array on error
        });
    }

    addRecords() {
        this.props.history.push('/add-records/_add');
    }

    logout() {
        this.props.history.push('/');
    }

    render() {
        return (
            <div>
                <h2 className="text-center">Records List</h2>
                <div className="row">
                    <button className="btn btn-primary" onClick={this.addRecords}>Add Records</button>
                    <button className="btn btn-secondary ml-2" onClick={this.logout}>Logout</button>
                </div>
                <br />
                <div className="row">
                    <input
                        type="text"
                        placeholder="Search Records"
                        value={this.state.searchTerm}
                        onChange={this.handleSearchChange}
                        className="form-control"
                    />
                    <button className="btn btn-primary" onClick={this.searchRecords} style={{ marginLeft: "10px" }}>Search</button>
                </div>
                <br />
                <div className="row">
                    <table className="table table-striped table-bordered">
                        <thead>
                            <tr>
                                <th>Id</th>
                                <th>Name</th>
                                <th>Description</th>
                                <th>Category</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            {
                                this.state.records.map(record =>
                                    <tr key={record.id}>
                                        <td>{record.id}</td>
                                        <td>{record.name}</td>
                                        <td>{record.description}</td>
                                        <td>{record.category ? record.category.name : 'Not Available'}</td>
                                        <td>
                                            <button onClick={() => this.editRecords(record.id)} className="btn btn-info">Update</button>
                                            <button style={{ marginLeft: "10px" }} onClick={() => this.deleteRecords(record.id)} className="btn btn-danger">Delete</button>
                                        </td>
                                    </tr>
                                )
                            }
                        </tbody>
                    </table>
                </div>
            </div>
        );
    }
}

export default ListRecordComponent;
