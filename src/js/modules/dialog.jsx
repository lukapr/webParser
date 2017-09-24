import React, {PropTypes} from 'react';
import Dialog from 'material-ui/Dialog';
import RaisedButton from 'material-ui/RaisedButton';
import TextField from 'material-ui/TextField';

import request from 'superagent';
import {notify} from 'react-notify-toast';

class ConfigDialog extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            name: "",
            description: "",
            link: "",
            id: "",
            openDialog: false,
            isEdit: false
        };
    }

    handleClose = () => {
        this.setState({
            openDialog: false,
            isEdit: false,
            name: "",
            description: "",
            link: "",
            id: ""
        });
        this.props.onClose();
    };

    componentWillReceiveProps = (nextProps) => {
        this.setState({
            openDialog: nextProps.isOpen,
            isEdit: nextProps.isEdit,
        });
        if (nextProps.isEdit) {
            this.setState({
                id: nextProps.config.id,
                name: nextProps.config.name,
                description: nextProps.config.description,
                link: nextProps.config.link,
            })
        }
    };

    handleSubmit = () => {
        this.state.isEdit ?
            request.put("/configs")
                .set('charset', '')
                .set('Accept', 'application/json')
                .set('Content-Type', ' application/json')
                .send(JSON.stringify({
                    "id": this.state.id,
                    "name": this.state.name,
                    "description": this.state.description,
                    "link": this.state.link
                })).end((err, res) => {
                if (err || !res.ok) {
                    notify.show('Error during updating configuration: ' + err.toString(), "error");
                    // this.handleClose();
                } else {
                    notify.show('Config was updated: ' + res.body, "success");
                    this.handleClose();
                }
            }) :
        request.post("/configs")
            .set('charset', '')
            .set('Accept', 'application/json')
            .set('Content-Type', ' application/json')
            .send(JSON.stringify({
                "name": this.state.name,
                "description": this.state.description,
                "link": this.state.link
            })).end((err, res) => {
            if (err || !res.ok) {
                notify.show('Error during adding new configuration: ' + err.toString(), "error");
                // this.handleClose();
            } else {
                notify.show('A new config was submitted: ' + res.body, "success");
                this.handleClose();
            }
        });
    };


    render() {

        const customContentStyle = {
            width: '35%',
            maxWidth: 'none',
        };

        const actions = [
            <RaisedButton
                label="Cancel"
                secondary={true}
                onClick={this.handleClose}
            />,
            <RaisedButton
                label="Submit"
                primary={true}
                onClick={this.handleSubmit}
            />,
        ];

        return (
            <div>
                <Dialog open={this.state.openDialog || this.state.isEdit}
                        title={this.props.title}
                        actions={actions}
                        modal={true}
                        contentStyle={customContentStyle}>
                    <TextField
                        onChange={(event) => {
                            this.setState({name: event.target.value})
                        }}
                        hintText="Name"
                        floatingLabelText="Name"
                        value={this.state.name}
                        fullWidth={true}
                    /><br/>
                    <TextField
                        onChange={(event) => {
                            this.setState({description: event.target.value})
                        }}
                        hintText="Description"
                        floatingLabelText="Description"
                        multiLine={true}
                        rows={1}
                        rowsMax={4}
                        value={this.state.description}
                        fullWidth={true}
                    /><br/>
                    <TextField
                        onChange={(event) => {
                            this.setState({link: event.target.value})
                        }}
                        hintText="Link"
                        floatingLabelText="Link"
                        value={this.state.link}
                        fullWidth={true}
                    />
                </Dialog>
            </div>
        );
    }
};

ConfigDialog
    .propTypes = {
    isOpen: PropTypes.bool,
    isEdit: PropTypes.bool,
    title: PropTypes.string,
    onClose: PropTypes.func
};

ConfigDialog.defaultProps = {
    isOpen: false,
    isEdit: false,
    title: ""
};

export default ConfigDialog;