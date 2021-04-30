package it.unive.lisa.analysis.nonrelational.value;

import it.unive.lisa.analysis.SemanticDomain.Satisfiability;
import it.unive.lisa.analysis.SemanticException;
import it.unive.lisa.analysis.nonrelational.value.BaseNonRelationalValueDomain;
import it.unive.lisa.analysis.nonrelational.value.NonRelationalValueDomain;
import it.unive.lisa.analysis.nonrelational.value.ValueEnvironment;
import it.unive.lisa.program.cfg.ProgramPoint;
import it.unive.lisa.symbolic.value.BinaryOperator;
import it.unive.lisa.symbolic.value.Constant;
import it.unive.lisa.symbolic.value.TernaryOperator;
import it.unive.lisa.symbolic.value.UnaryOperator;
import it.unive.lisa.symbolic.value.ValueExpression;

public class SignDomain extends BaseNonRelationalValueDomain<SignDomain> {
	
	enum Sign {
		PLUS, MINUS, ZERO, TOP, BOTTOM
	}
	private Signs sign;
	
	public SignDomain() {
		this(Signs.TOP);
	}
	private SignDomain(Signs sign) {
		this.sign = sign;
	}
	@Override
	public int hashCode() {
		int prime = 53;
		if(sign == null){
			return prime;
		}
		else{
			return (prime + sign.hashCode());
		}
	}
	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;

		if (getClass() != object.getClass())
			return false;

		SignDomain other = (SignDomain) object;
		if (sign != other.sign)
			return false;

		return true;
	}

	@Override
	public SignDomain top() {
		return new SignDomain(Signs.TOP);
	}

	@Override
	public boolean isTop() {
		return this.sign == Signs.TOP;
	}

	@Override
	public SignDomain bottom() {
		return new SignDomain(Signs.BOTTOM);
	}
	
	@Override
	public boolean isBottom() {
		return this.sign == Signs.BOTTOM;
	}

	@Override
	public String representation() {
		// TODO Auto-generated method stub
		return this.sign.name();
	}

	@Override
	protected SignDomain evalNullConstant(ProgramPoint programPoint) {
		return top();
	}

	@Override
	protected SignDomain evalNonNullConstant(Constant constant, ProgramPoint programPoint) {
		if (constant.getValue() instanceof Integer) {
			int constantValue = (int) constant.getValue(); // typecast constant value to int
			if (constantValue == 0)
				return new SignDomain(Signs.ZERO);
			else if (constantValue > 0)
				return new SignDomain(Signs.PLUS);
			else
				return new SignDomain(Signs.MINUS);
		}
		return top();
	}

	@Override
	protected SignDomain evalUnaryExpression(UnaryOperator unaryOperator, SignDomain signDomain, ProgramPoint programPoint) {
		switch(unaryOperator) {
			case NUMERIC_NEG:
				if (signDomain.sign == Signs.PLUS) {
					return new SignDomain(Signs.MINUS);
				} else if (signDomain.sign == Signs.ZERO) {
					return new SignDomain(Signs.ZERO);
				} else if (signDomain.sign == Signs.MINUS) {
					return new SignDomain(Signs.PLUS);
				}
			case LOGICAL_NOT:
			case STRING_LENGTH:
			case TYPEOF:
			default:
				return top();

		}
	}
	@Override
	protected SignDomain evalBinaryExpression(BinaryOperator binaryOperator, SignDomain left, SignDomain right,
			ProgramPoint programPoint) {
		switch(binaryOperator) {
			case LOGICAL_AND:
			case LOGICAL_OR:
			case COMPARISON_GE:
			case COMPARISON_EQ:
			case COMPARISON_GT:
			case COMPARISON_LT:
			case COMPARISON_NE:
			case COMPARISON_LE:
			case NUMERIC_SUB:
			case NUMERIC_MUL:
			case NUMERIC_DIV:
			case NUMERIC_MOD:
			case STRING_CONCAT:
			case STRING_CONTAINS:
			case STRING_ENDS_WITH:
			case STRING_EQUALS:
			case STRING_INDEX_OF:
			case STRING_STARTS_WITH:
			case TYPE_CAST:
			case TYPE_CHECK:
				return top();

			case NUMERIC_ADD:
				switch(left.sign) {
					case PLUS:
						switch(right.sign) {
							case MINUS:
								return top();
							case PLUS:
								return left;
							case ZERO:
								return left;
							case TOP:
							case BOTTOM:
							default:
								return top();
						}
					case MINUS:
						switch(right.sign) {
							case MINUS:
								return left;
							case PLUS:
								return top();
							case ZERO:
								return left;
							case TOP:
							case BOTTOM:
							default:
								return top();
						}
					case ZERO:
						switch(right.sign) {
							case MINUS:
								return right;
							case PLUS:
								return right;
							case ZERO:
								return left;
							case TOP:
							case BOTTOM:
							default:
								return top();
						}

					case BOTTOM:
					case TOP:
					default:
						return top();
				}
			default:
				return top();
		}
	}
	@Override
	protected SignDomain evalTernaryExpression(TernaryOperator ternaryOperator, SignDomain left, SignDomain middle,
			SignDomain right, ProgramPoint programPoint) {
		switch(ternaryOperator) {
			case STRING_REPLACE:
			case STRING_SUBSTRING:
			default:
				return top();
		}
	}
	@Override
	protected Satisfiability satisfiesAbstractValue(SignDomain value, ProgramPoint programPoint) {
		return Satisfiability.UNKNOWN;
	}
	@Override
	protected Satisfiability satisfiesNullConstant(ProgramPoint programPoint) {
		return Satisfiability.UNKNOWN;
	}
	@Override
	protected Satisfiability satisfiesNonNullConstant(Constant constant, ProgramPoint programPoint) {
		return Satisfiability.UNKNOWN;
	}
	@Override
	protected Satisfiability satisfiesUnaryExpression(UnaryOperator operator, SignDomain arg, ProgramPoint programPoint) {
		return Satisfiability.UNKNOWN;
	}
	@Override
	protected Satisfiability satisfiesBinaryExpression(BinaryOperator binaryOperator, SignDomain left, SignDomain right,
			ProgramPoint programPoint) {
		return Satisfiability.UNKNOWN;
	}
	@Override
	protected Satisfiability satisfiesTernaryExpression(TernaryOperator ternaryOperator, SignDomain left, SignDomain middle,
			SignDomain right, ProgramPoint programPoint) {
		return Satisfiability.UNKNOWN;
	}
	@Override
	protected SignDomain lubAux(SignDomain other) throws SemanticException {
		return top();
	}
	@Override
	protected SignDomain wideningAux(SignDomain other) throws SemanticException {
		return lubAux(other);
	}
	@Override
	protected boolean lessOrEqualAux(SignDomain other) throws SemanticException {
		// TODO Auto-generated method stub
		return false;
	}

}
