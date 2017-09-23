import React, {Component} from 'react';
import request from 'superagent';
import {notify} from 'react-notify-toast';
import {
    TableRow,
    TableRowColumn,
} from 'material-ui/Table';
import RaisedButton from 'material-ui/RaisedButton';

class Config extends Component {
    constructor(props) {
        super(props);
        this.state = {
            display: true
        };
    };

    handleDelete = () => {
        request.delete("configs/" + this.props.config.id)
            .end((err, res) => {
                if (err || !res.ok) {
                    notify.show('Error during deleting: ' + err, "error");
                } else {
                    notify.show('yay got ' + JSON.stringify(res.body), "success");
                    this.setState({display: false})
                }
            });
    };

    render() {
        const style = {
            margin: 12,
        };
        return ( this.state.display ?
            <TableRow>
                <TableRowColumn>{this.props.config.name}</TableRowColumn>
                <TableRowColumn>{this.props.config.description}</TableRowColumn>
                <TableRowColumn>{this.props.config.link}</TableRowColumn>
                <TableRowColumn>
                    <RaisedButton label="Delete" secondary={true} style={style} onClick={this.handleDelete}/>
                </TableRowColumn>
            </TableRow>
            : null);
    }
}

export default Config;