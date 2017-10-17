import React, {Component, PropTypes} from 'react';
import request from 'superagent';
import {notify} from 'react-notify-toast';
import {
    TableRow,
    TableRowColumn,
} from 'material-ui/Table';
import IconButton from 'material-ui/IconButton';
import ActionDelete from 'material-ui/svg-icons/action/delete'
import EditorModeEdit from 'material-ui/svg-icons/editor/mode-edit'
import AvPlayArrow from 'material-ui/svg-icons/av/play-arrow'
import ConfigDialog from "./dialog";

class Config extends Component {
    constructor(props) {
        super(props);
        this.state = {
            display: true,
            edit: false
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

    handleOpen = () => {
        this.setState({edit: true});
    };

    handleClose = () => {
        this.setState({edit: false});
        this.props.onEdit();
    };

    handleProcess = () => {
        request.post("configs/process/" + this.props.config.id)
            .end((err, res) => {
                if (err || !res.ok) {
                    notify.show('Error during processing: ' + err, "error");
                } else {
                    notify.show('yay got ' + JSON.stringify(res.body), "success");
                }
            });
    };


    render() {
        return ( this.state.display ?
            <TableRow>
                <TableRowColumn>{this.props.config.name}</TableRowColumn>
                <TableRowColumn>{this.props.config.description}</TableRowColumn>
                <TableRowColumn>{this.props.config.link}</TableRowColumn>
                <TableRowColumn>
                    <IconButton onClick={this.handleDelete}>
                        <ActionDelete/>
                    </IconButton>
                </TableRowColumn>
                <TableRowColumn>
                    <ConfigDialog onClose={this.handleClose} isEdit={this.state.edit} title={"Edit config"}
                                  config={this.props.config}/>
                    <IconButton onClick={this.handleOpen}>
                        <EditorModeEdit/>
                    </IconButton>
                </TableRowColumn>
                <TableRowColumn>
                    <IconButton onClick={this.handleProcess}>
                        <AvPlayArrow/>
                    </IconButton>
                </TableRowColumn>
            </TableRow>
            : null);
    }
}

Config.propTypes = {
    onEdit: PropTypes.func
}

export default Config;