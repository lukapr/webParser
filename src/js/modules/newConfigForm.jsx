import React, {PropTypes} from 'react';
import Dialog from 'material-ui/Dialog';
import FlatButton from 'material-ui/FlatButton';
import RaisedButton from 'material-ui/RaisedButton';
import TextField from 'material-ui/TextField';

import request from 'superagent';
import {notify} from 'react-notify-toast';

class NewConfigForm extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            name: "",
            description: "",
            link: "",
            openDialog: false
        };
    }

    handleOpen = () => {
        this.setState({
            openDialog: true
        });
    }

    handleClose = () => {
        this.setState({
            openDialog: false
        });
        this.props.onClose();
    }

    handleSubmit = () => {
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
            width: '45%',
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
                <RaisedButton label="Add new Config" primary={true} onClick={this.handleOpen}/>
                <Dialog open={this.state.openDialog}
                        title="Adding new Config"
                        actions={actions}
                        modal={true}
                        contentStyle={customContentStyle}>
                    <TextField
                        onChange={(event) => {
                            this.setState({name: event.target.value})
                        }}
                        hintText="Name"
                        floatingLabelText="Name"
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
                    /><br/>
                    <TextField
                        onChange={(event) => {
                            this.setState({link: event.target.value})
                        }}
                        hintText="Link"
                        floatingLabelText="Link"
                    />
                </Dialog>
            </div>
        );
    }
};

NewConfigForm
    .propTypes = {
    onClose: PropTypes.func,
};

export default NewConfigForm;