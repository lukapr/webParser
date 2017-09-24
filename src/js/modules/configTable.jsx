import React, {Component, PropTypes} from 'react';
import Config from './config';
import {
    Table,
    TableBody,
    TableHeader,
    TableHeaderColumn,
    TableRow
} from 'material-ui/Table';

class ConfigTable extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        const result = [];
        this.props.configs.forEach((config) => {
            result.push(<Config config={config} key={config.id} onEdit={this.props.onEdit}/>
            )
        });
        return (
            <div style={{width: '100%'}}>
                <Table fixedHeader={false} style={{ tableLayout: 'auto'}}>
                    <TableHeader adjustForCheckbox={false} displaySelectAll={false}>
                        <TableRow >
                            <TableHeaderColumn>Name</TableHeaderColumn>
                            <TableHeaderColumn>Description</TableHeaderColumn>
                            <TableHeaderColumn>Link</TableHeaderColumn>
                            <TableHeaderColumn>Delete?</TableHeaderColumn>
                            <TableHeaderColumn>Change?</TableHeaderColumn>
                        </TableRow>
                    </TableHeader>
                    <TableBody displayRowCheckbox={false}>
                        {result}
                    </TableBody>
                </Table>
            </div>
        )
    }
}

ConfigTable.propTypes = {
    onEdit: PropTypes.func
}

export default ConfigTable;