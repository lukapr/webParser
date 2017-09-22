import React from 'react';
import ReactDOM from 'react-dom';
import NewConfigForm from "./modules/newConfigForm";

var Config = React.createClass({
    getInitialState: function () {
        return {display: true};
    },

    handleDelete() {
        var self = this;
        $.ajax({
            url: "configs/"+self.props.config.id,
            type: 'DELETE',
            success: function(result) {
                self.setState({display: false});
            },
            error: function(xhr, ajaxOptions, thrownError) {
                toastr.error(xhr.responseJSON.message);
            }
        });
    },

    render: function () {
        if (this.state.display===false) return null;
        else return (
            <tr>
                <td>{this.props.config.name}</td>
                <td>{this.props.config.description}</td>
                <td>{this.props.config.link}</td>
                <td>
                    <button className="btn btn-info" onClick={this.handleDelete}>Delete</button>
                </td>
            </tr>);
    }
});
var ConfigTable = React.createClass({
    render: function () {
        var rows = [];
        this.props.configs.forEach(function (config) {
            rows.push(
                <Config config={config} key={config.name} />);
        });
        return (
            <div className="container">
                <table className="table table-striped">
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>description</th>
                        <th>link</th>
                    </tr>
                    </thead>
                    <tbody>{rows}</tbody>
                </table>
            </div>);
    }
});
var App = React.createClass({

    loadConfigsFromServer: function () {
        var self = this;
        $.ajax({
            url: "/configs"
        }).then(function (data) {
            self.setState({configs: data});
        });
    },

    getInitialState: function () {
        return {configs: [], showModal: false};
    },

    componentDidMount: function () {
        this.loadConfigsFromServer();
    },

    componentDidUpdate: function () {
        if (this.state.needToRender) {
            this.loadConfigsFromServer();
            this.setState({needToRender: false});
        }
    },

    onPress: function () {
        var self = this;
        $.ajax({
            url: "/configs"
        }).then(function (data) {
            self.setState({configs: data});
        });
    },

    openModal(event) {
        this.setState({showModal: true});
    },
    closeModal(event){
        this.setState({showModal: false});
        this.setState({needToRender: true});
    },

    render() {
        return ( <div className="component-app">
            <ConfigTable configs={this.state.configs}/>
            <a onClick={this.openModal}>
                Open A Modal
                {this.state.showModal ?
                    <NewConfigForm onClose={this.closeModal}/>
                    : null}
            </a>
        </div>);
    }
});
ReactDOM.render(<App/>, document.getElementById('root'));