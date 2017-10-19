import React, {Component, PropTypes} from 'react';
import request from 'superagent';
import {
    TableRow,
    TableRowColumn,
} from 'material-ui/Table';
import IconButton from 'material-ui/IconButton';
import ActionDelete from 'material-ui/svg-icons/action/delete'
import EditorModeEdit from 'material-ui/svg-icons/editor/mode-edit'
import AvPlayArrow from 'material-ui/svg-icons/av/play-arrow'
import AlertError from 'material-ui/svg-icons/alert/error'
import ActionDone from 'material-ui/svg-icons/action/done'
import ConfigDialog from "./dialog.jsx";
import CircularProgress from 'material-ui/CircularProgress';
import {red500, green500} from 'material-ui/styles/colors';
import {ErrorNotification, SuccessNotification} from "./notifications.jsx";

class Config extends Component {
    constructor(props) {
        super(props);
        this.state = {
            display: true,
            edit: false,
            parsing: ""
        };
    };

    handleDelete = () => {
        request.delete("configs/" + this.props.config.id)
            .end((err, res) => {
                if (err || !res.ok) {
                    this.refs.errorNotification.handleOpen('Error during deleting: ' + err);
                } else {
                    this.refs.successNotification.handleOpen('Successfully delete');
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
        this.setState({parsing: "PENDING"});
        request.post("configs/process/" + this.props.config.id)
            .end((err, res) => {
                if (err || !res.ok) {
                    this.refs.errorNotification.handleOpen('Error during processing: ' + err);
                    this.setState({parsing: "FAILED"});
                } else {
                    this.refs.successNotification.handleOpen('Parsing started');
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
                    this.refs.errorNotification.handleOpen('Parsing failed: ' + err);
                    clearInterval(this.counter);
                    this.setState({parsing: "FAILED"});
                } else {
                    if (res.body.status === 'SUCCESS') {
                        clearInterval(this.counter);
                        this.setState({parsing: "SUCCESS"});
                        this.refs.successNotification.handleOpen('Successfully parsed');
                    } else if (res.body.status === 'PENDING' || res.body.status === 'CREATED') {
                    } else {
                        clearInterval(this.counter);
                        this.setState({parsing: "FAILED"});
                        this.refs.errorNotification.handleOpen('Parsing failed');
                    }
                }
            })
    };


    render() {
        let icon = null;
        if (this.state.parsing === "PENDING") {
            icon = <CircularProgress/>
        } else {
            icon = <IconButton onClick={this.handleProcess}>
                <AvPlayArrow/>
            </IconButton>
            if (this.state.parsing === "FAILED") {
                icon = <div><IconButton onClick={this.handleProcess}>
                    <AvPlayArrow/>
                </IconButton>
                    <AlertError color={red500}/>
                </div>
            } else if (this.state.parsing === "SUCCESS") {
                icon = <div><IconButton onClick={this.handleProcess}>
                    <AvPlayArrow/>
                </IconButton>
                    <ActionDone color={green500}/>
                </div>
            }
        }
        return ( this.state.display ?
            <TableRow>
                <TableRowColumn>{this.props.config.name}</TableRowColumn>
                <TableRowColumn>{this.props.config.description}</TableRowColumn>
                <TableRowColumn>{this.props.config.link}</TableRowColumn>
                <TableRowColumn>
                    <ErrorNotification ref='errorNotification'/>
                    <SuccessNotification ref='successNotification'/>
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
                    {icon}
                </TableRowColumn>
            </TableRow>
            : null);
    }
}

Config.propTypes = {
    onEdit: PropTypes.func
}

export default Config;