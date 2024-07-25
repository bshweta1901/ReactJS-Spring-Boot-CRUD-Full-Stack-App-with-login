import React, { Component } from 'react';
import RecordService from '../services/RecordsService';

class CreateRecordComponent extends Component {
    constructor(props) {
        super(props);

        this.state = {
            id: this.props.match.params.id,
            name: '',
            description: '',
            category: '', // Ensure this is a string to match option values
            categoryList: []
        };
        this.changeNameHandler = this.changeNameHandler.bind(this);
        this.changeDescriptionHandler = this.changeDescriptionHandler.bind(this);
        this.changeCategoryHandler = this.changeCategoryHandler.bind(this);
        this.saveOrUpdateRecord = this.saveOrUpdateRecord.bind(this);
    }

    componentDidMount() {
        // Fetch categories
        RecordService.getCategoryList({ entityType: 'CATEGORY' }).then((res) => {
            console.log('Category List:', res.data); // Debugging line
            this.setState({ categoryList: res.data });
        });

        if (this.state.id !== '_add') {
            RecordService.getRecordsById(this.state.id).then((res) => {
                let record = res.data;
                console.log('Fetched Record:', record); // Debugging line
                this.setState({
                    name: record.name || '',
                    description: record.description || '',
                    category: record.category ? record.category.id : '' // Ensure correct format
                });
            });
        }
    }

    saveOrUpdateRecord = (e) => {
        e.preventDefault();
        let record ;
    
        if (this.state.id === '_add') {
            // Prepare record for creation
            record = {
                name: this.state.name,
                description: this.state.description,
                category: this.state.category // Ensure this matches the expected format
            };
            
            console.log('Creating record => ' + JSON.stringify(record));
    
            RecordService.createRecords(record).then(res => {
                this.props.history.push('/records');
            }).catch(error => {
                console.error('Error creating record:', error);
            });
        } else {
            record = {
                id:this.state.id,
                name: this.state.name,
                description: this.state.description,
                category: this.state.category // Ensure this matches the expected format
            };
            
            // Prepare record for update with only id
        
            console.log('Updating record => ' + JSON.stringify(record));
    
            RecordService.updateRecords(record).then(res => {
                this.props.history.push('/records');
            }).catch(error => {
                console.error('Error updating record:', error);
            });
        }
    }
    

    deleteRecord = () => {
        RecordService.deleteRecord(this.state.id).then(res => {
            this.props.history.push('/records');
        });
    }

    changeNameHandler = (event) => {
        this.setState({ name: event.target.value });
    }

    changeDescriptionHandler = (event) => {
        this.setState({ description: event.target.value });
    }

    changeCategoryHandler = (event) => {
        const selectedCategoryId = event.target.value;
        console.log('Selected Category ID:', selectedCategoryId); // Debugging line
        this.setState({ category: {id:selectedCategoryId }});
    }

    cancel = () => {
        this.props.history.push('/records');
    }

    getTitle() {
        if (this.state.id === '_add') {
            return <h3 className="text-center">Add Record</h3>
        } else {
            return <h3 className="text-center">Update Record</h3>
        }
    }

    render() {
        return (
            <div>
                <br />
                <div className="container">
                    <div className="row">
                        <div className="card col-md-6 offset-md-3 offset-md-3">
                            {this.getTitle()}
                            <div className="card-body">
                                <form>
                                    <div className="form-group">
                                        <label> Name: </label>
                                        <input 
                                            placeholder="Name" 
                                            name="name" 
                                            className="form-control" 
                                            value={this.state.name} 
                                            onChange={this.changeNameHandler}
                                        />
                                    </div>
                                    <div className="form-group">
                                        <label> Description: </label>
                                        <input 
                                            placeholder="Description" 
                                            name="description" 
                                            className="form-control" 
                                            value={this.state.description} 
                                            onChange={this.changeDescriptionHandler}
                                        />
                                    </div>
                                    <div className="form-group">
                                        <label> Category: </label>
                                        <select 
                                            name="category" 
                                            className="form-control" 
                                            value={this.state.category.id || ''} // Ensure value is correctly bound
                                            onChange={this.changeCategoryHandler}
                                        >
                                            <option value="">Select Category</option>
                                            {this.state.categoryList.map(cat => (
                                                <option key={cat.id} value={cat.id}>
                                                    {cat.name}
                                                </option>
                                            ))}
                                        </select>
                                    </div>
                                    <button 
                                        className="btn btn-success" 
                                        onClick={this.saveOrUpdateRecord}
                                    >
                                        Save
                                    </button>
                                    {this.state.id !== '_add' && (
                                        <button 
                                            className="btn btn-danger" 
                                            onClick={this.deleteRecord} 
                                            style={{ marginLeft: "10px" }}
                                        >
                                            Delete
                                        </button>
                                    )}
                                    <button 
                                        className="btn btn-danger" 
                                        onClick={this.cancel} 
                                        style={{ marginLeft: "10px" }}
                                    >
                                        Cancel
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default CreateRecordComponent;
