import React, {PropTypes} from 'react';
import RaisedButton from 'material-ui/RaisedButton';
import ConfigDialog from './dialog';


class NewConfigForm extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            openDialog: false
        }
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

    render() {
        return (
            <div>
                <RaisedButton label="Add new Config" primary={true} onClick={this.handleOpen}/>
                <ConfigDialog onClose={this.handleClose} isOpen={this.state.openDialog} title={"Add new config"}/>
            </div>
        );
    }
};

NewConfigForm
    .propTypes = {
    onClose: PropTypes.func,
};

export default NewConfigForm;