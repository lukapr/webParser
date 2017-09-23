import React, {Component} from 'react';
import request from 'superagent';
import {notify} from 'react-notify-toast';

class Config extends Component {
    constructor(props) {
        super(props);
        this.state = {
            display: true
        };
        this.handleDelete.bind(this);
    }

    handleDelete() {
        request.delete("configs/" + self.props.config.id)
            .end(function (err, res) {
                if (err || !res.ok) {
                    notify.show('Error during deleting: ' + err, "error");
                } else {
                    notify.show('yay got ' + JSON.stringify(res.body), "success");
                    this.setState({display: false})
                }
            });
    }

    render() {
        return ( this.state.display ?
            <tr>
                <td>{this.props.config.name}</td>
                <td>{this.props.config.description}</td>
                <td>{this.props.config.link}</td>
                <td>
                    <button className="btn btn-info" onClick={this.handleDelete}>Delete</button>
                </td>
            </tr>
            : null);
    }
}

export default Config;