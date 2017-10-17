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
import CircularProgress from 'material-ui/CircularProgress';

class Config extends Component {
    constructor(props) {
        super(props);
        this.state = {
            display: true,
            edit: false,
            parsing: false
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
        this.setState({parsing: true});
        request.post("configs/process/" + this.props.config.id)
            .end((err, res) => {
                if (err || !res.ok) {
                    notify.show('Error during processing: ' + err, "error");
                    this.setState({parsing: false});
                } else {
                    notify.show('Parsing started', "success");
                    this.setState({
                        taskId: res.body
                    });
                    this.counter = setInterval(this.checkResult, 1000);
                }
            });
    };

    checkResult = () => {
        request.get("tasks/" + this.state.taskId)
            .end((err, res) => {
                if (err || !res.ok) {
                    notify.show('Error during getting task: ' + err, "error");
                    clearInterval(this.counter);
                    this.setState({parsing: false});
                } else {
                    if (res.body.status === 'SUCCESS') {
                        clearInterval(this.counter);
                        this.setState({parsing: false});
                        notify.show('Successfully parsed', "success");
                    } else if (res.body.status === 'FAILED') {
                        clearInterval(this.counter);
                        this.setState({parsing: false});
                        notify.show('Error during getting task: ' + err, "error");
                    }
                }
            })
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
                    {
                        (this.state.parsing) ?
                            <CircularProgress/> :
                            <IconButton onClick={this.handleProcess}>
                                <AvPlayArrow/>
                            </IconButton>
                    }
                </TableRowColumn>
            </TableRow>
            : null);
    }
}

Config.propTypes = {
    onEdit: PropTypes.func
}

export default Config;